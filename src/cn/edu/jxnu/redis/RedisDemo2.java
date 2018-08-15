package cn.edu.jxnu.redis;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @description 第二章 java版代码
 * @author Mr.Li
 * 
 */
public class RedisDemo2 {
	/**
	 * @description 主函数
	 * @param args
	 * @throws InterruptedException
	 */
	public static final void main(String[] args) throws InterruptedException {
		new RedisDemo2().run();
	}

	public void run() throws InterruptedException {
		Jedis conn = new Jedis("localhost");
		conn.select(15);

		testLoginCookies(conn);
		testShopppingCartCookies(conn);
		testCacheRows(conn);
		testCacheRequest(conn);
	}

	public void testLoginCookies(Jedis conn) throws InterruptedException {
		System.out.println("\n----- testLoginCookies -----");
		String token = UUID.randomUUID().toString();

		updateToken(conn, token, "username", "itemX");
		System.out.println("We just logged-in/updated token: " + token);
		System.out.println("For user: 'username'");
		System.out.println();

		System.out
				.println("What username do we get when we look-up that token?");
		String r = checkToken(conn, token);
		System.out.println(r);
		System.out.println();
		assert r != null;

		System.out
				.println("Let's drop the maximum number of cookies to 0 to clean them out");
		System.out
				.println("We will start a thread to do the cleaning, while we stop it later");

		CleanSessionsThread thread = new CleanSessionsThread(0);
		thread.start();
		Thread.sleep(1000);
		thread.quit();
		Thread.sleep(2000);
		if (thread.isAlive()) {
			throw new RuntimeException(
					"The clean sessions thread is still alive?!?");
		}

		long s = conn.hlen("login:");
		System.out
				.println("The current number of sessions still available is: "
						+ s);
		assert s == 0;
	}

	public void testShopppingCartCookies(Jedis conn)
			throws InterruptedException {
		System.out.println("\n----- testShopppingCartCookies -----");
		String token = UUID.randomUUID().toString();

		System.out.println("We'll refresh our session...");
		updateToken(conn, token, "username", "itemX");
		System.out.println("And add an item to the shopping cart");
		addToCart(conn, token, "itemY", 3);
		Map<String, String> r = conn.hgetAll("cart:" + token);
		System.out.println("Our shopping cart currently has:");
		for (Map.Entry<String, String> entry : r.entrySet()) {
			System.out.println("  " + entry.getKey() + ": " + entry.getValue());
		}
		System.out.println();

		assert r.size() >= 1;

		System.out.println("Let's clean out our sessions and carts");
		CleanFullSessionsThread thread = new CleanFullSessionsThread(0);
		thread.start();
		Thread.sleep(1000);
		thread.quit();
		Thread.sleep(2000);
		if (thread.isAlive()) {
			throw new RuntimeException(
					"The clean sessions thread is still alive?!?");
		}

		r = conn.hgetAll("cart:" + token);
		System.out.println("Our shopping cart now contains:");
		for (Map.Entry<String, String> entry : r.entrySet()) {
			System.out.println("  " + entry.getKey() + ": " + entry.getValue());
		}
		assert r.size() == 0;
	}

	public void testCacheRows(Jedis conn) throws InterruptedException {
		System.out.println("\n----- testCacheRows -----");
		System.out
				.println("First, let's schedule caching of itemX every 5 seconds");
		scheduleRowCache(conn, "itemX", 5);
		System.out.println("Our schedule looks like:");
		Set<Tuple> s = conn.zrangeWithScores("schedule:", 0, -1);
		for (Tuple tuple : s) {
			System.out.println("  " + tuple.getElement() + ", "
					+ tuple.getScore());
		}
		assert s.size() != 0;

		System.out
				.println("We'll start a caching thread that will cache the data...");

		CacheRowsThread thread = new CacheRowsThread();
		thread.start();

		Thread.sleep(1000);
		System.out.println("Our cached data looks like:");
		String r = conn.get("inv:itemX");
		System.out.println(r);
		assert r != null;
		System.out.println();

		System.out.println("We'll check again in 5 seconds...");
		Thread.sleep(5000);
		System.out.println("Notice that the data has changed...");
		String r2 = conn.get("inv:itemX");
		System.out.println(r2);
		System.out.println();
		assert r2 != null;
		assert !r.equals(r2);

		System.out.println("Let's force un-caching");
		scheduleRowCache(conn, "itemX", -1);
		Thread.sleep(1000);
		r = conn.get("inv:itemX");
		System.out.println("The cache was cleared? " + (r == null));
		assert r == null;

		thread.quit();
		Thread.sleep(2000);
		if (thread.isAlive()) {
			throw new RuntimeException(
					"The database caching thread is still alive?!?");
		}
	}

	public void testCacheRequest(Jedis conn) {
		System.out.println("\n----- testCacheRequest -----");
		String token = UUID.randomUUID().toString();

		Callback callback = new Callback() {
			public String call(String request) {
				return "content for " + request;
			}
		};

		updateToken(conn, token, "username", "itemX");
		String url = "http://test.com/?item=itemX";
		System.out.println("We are going to cache a simple request against "
				+ url);
		String result = cacheRequest(conn, url, callback);
		System.out.println("We got initial content:\n" + result);
		System.out.println();

		assert result != null;

		System.out
				.println("To test that we've cached the request, we'll pass a bad callback");
		String result2 = cacheRequest(conn, url, null);
		System.out
				.println("We ended up getting the same response!\n" + result2);

		assert result.equals(result2);

		assert !canCache(conn, "http://test.com/");
		assert !canCache(conn, "http://test.com/?item=itemX&_=1234536");
	}

	/**
	 * @description 获取并返回令牌对应的用户
	 * @param conn
	 * @param token
	 * @return
	 */
	public String checkToken(Jedis conn, String token) {
		return conn.hget("login:", token);
	}

	/**
	 * @description 记录用户最后一次访问的时间与浏览的商品
	 * @param conn
	 * @param token
	 * @param user
	 * @param item
	 */
	public void updateToken(Jedis conn, String token, String user, String item) {
		long timestamp = System.currentTimeMillis() / 1000;
		/* 令牌与登录用户之间的映射 */
		conn.hset("login:", token, user);
		/* 记录令牌最后一次出现的时间 */
		conn.zadd("recent:", timestamp, token);
		if (item != null) {
			/* 记录浏览过的商品 */
			conn.zadd("viewed:" + token, timestamp, item);
			/* 移除旧的记录，只保留最近的25个商品 */
			conn.zremrangeByRank("viewed:" + token, 0, -26);
			/* 记录浏览次数 */
			conn.zincrby("viewed:", -1, item);
		}
	}

	/**
	 * @description 添加商品到购物车
	 * @param conn
	 * @param session
	 * @param item
	 * @param count
	 */
	public void addToCart(Jedis conn, String session, String item, int count) {
		if (count <= 0) {
			conn.hdel("cart:" + session, item);
		} else {
			conn.hset("cart:" + session, item, String.valueOf(count));
		}
	}

	/**
	 * @descript 缓存调度和设置延时值 小于0为去除
	 * @param conn
	 * @param rowId
	 * @param delay
	 */
	public void scheduleRowCache(Jedis conn, String rowId, int delay) {
		conn.zadd("delay:", delay, rowId);
		conn.zadd("schedule:", System.currentTimeMillis() / 1000, rowId);
	}

	/**
	 * @description 缓存请求
	 * @param conn
	 * @param request
	 * @param callback
	 * @return
	 */
	public String cacheRequest(Jedis conn, String request, Callback callback) {
		/* 不能缓存，调用回调函数 */
		if (!canCache(conn, request)) {
			return callback != null ? callback.call(request) : null;
		}

		String pageKey = "cache:" + hashRequest(request);
		String content = conn.get(pageKey);
		/* 没有被缓存，继续生成页面 */
		if (content == null && callback != null) {
			content = callback.call(request);
			/* 缓存5分钟 */
			conn.setex(pageKey, 300, content);
		}

		return content;
	}

	/**
	 * @description 判断是否能被缓存
	 * @param conn
	 * @param request
	 * @return
	 */
	public boolean canCache(Jedis conn, String request) {
		try {
			URL url = new URL(request);
			HashMap<String, String> params = new HashMap<String, String>();
			if (url.getQuery() != null) {
				for (String param : url.getQuery().split("&")) {
					String[] pair = param.split("=", 2);
					params.put(pair[0], pair.length == 2 ? pair[1] : null);
				}
			}
			/* 尝试取出商品id */
			String itemId = extractItemId(params);
			/* 是否为缓存，是否是商品 */
			if (itemId == null || isDynamic(params)) {
				return false;
			}
			/* 浏览次数排名 */
			Long rank = conn.zrank("viewed:", itemId);
			/* 只缓存10000个 */
			return rank != null && rank < 10000;
		} catch (MalformedURLException mue) {
			return false;
		}
	}

	/**
	 * @description 判断是否为商品
	 * @param params
	 * @return
	 */
	public boolean isDynamic(Map<String, String> params) {
		return params.containsKey("_");
	}

	public String extractItemId(Map<String, String> params) {
		return params.get("item");
	}

	public String hashRequest(String request) {
		return String.valueOf(request.hashCode());
	}

	public interface Callback {
		public String call(String request);
	}

	/**
	 * @description 清理旧会话 独立线程
	 * @author Mr.Li
	 * 
	 */
	public class CleanSessionsThread extends Thread {
		private Jedis conn;
		private int limit;
		private boolean quit;

		public CleanSessionsThread(int limit) {
			this.conn = new Jedis("localhost");
			this.conn.select(15);
			this.limit = limit;
		}

		public void quit() {
			quit = true;
		}

		/* 当退出了才需要清理 */
		public void run() {
			while (!quit) {
				long size = conn.zcard("recent:");
				/* 令牌数量没有超过限制 */
				if (size <= limit) {
					try {
						sleep(1000);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
					continue;
				}

				/* 获取需要清理的令牌数量 */
				long endIndex = Math.min(size - limit, 100);
				/* 得到需要清理的令牌id */
				Set<String> tokenSet = conn.zrange("recent:", 0, endIndex - 1);
				String[] tokens = tokenSet.toArray(new String[tokenSet.size()]);

				ArrayList<String> sessionKeys = new ArrayList<String>();
				for (String token : tokens) {
					sessionKeys.add("viewed:" + token);
				}
				/* 删除用户与 最近登录会话，不定长参数 */
				conn.del(sessionKeys.toArray(new String[sessionKeys.size()]));
				conn.hdel("login:", tokens);
				conn.zrem("recent:", tokens);
			}
		}
	}

	/**
	 * @description 新的清理函数 同上，增加清理购物车
	 * @author Mr.Li
	 * 
	 */
	public class CleanFullSessionsThread extends Thread {
		private Jedis conn;
		private int limit;
		private boolean quit;

		public CleanFullSessionsThread(int limit) {
			this.conn = new Jedis("localhost");
			this.conn.select(15);
			this.limit = limit;
		}

		public void quit() {
			quit = true;
		}

		public void run() {
			while (!quit) {
				long size = conn.zcard("recent:");
				if (size <= limit) {
					try {
						sleep(1000);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
					continue;
				}

				long endIndex = Math.min(size - limit, 100);
				Set<String> sessionSet = conn
						.zrange("recent:", 0, endIndex - 1);
				String[] sessions = sessionSet.toArray(new String[sessionSet
						.size()]);

				ArrayList<String> sessionKeys = new ArrayList<String>();
				for (String sess : sessions) {
					sessionKeys.add("viewed:" + sess);
					/* 增加这行代码，清理购物车 */
					sessionKeys.add("cart:" + sess);
				}
				/* list集合转化为array数组，需要构建一个字符串数组长度是list集合的个数 */
				conn.del(sessionKeys.toArray(new String[sessionKeys.size()]));
				conn.hdel("login:", sessions);
				conn.zrem("recent:", sessions);
			}
		}
	}

	/**
	 * @description 数据行缓存 独立线程
	 * @author Mr.Li
	 * 
	 */
	public class CacheRowsThread extends Thread {
		private Jedis conn;
		private boolean quit;

		public CacheRowsThread() {
			this.conn = new Jedis("localhost");
			this.conn.select(15);
		}

		public void quit() {
			quit = true;
		}

		public void run() {
			Gson gson = new Gson();
			while (!quit) {
				Set<Tuple> range = conn.zrangeWithScores("schedule:", 0, 0);
				Tuple next = range.size() > 0 ? range.iterator().next() : null;
				long now = System.currentTimeMillis() / 1000;
				/* 暂时没有需要缓存的行，休息 */
				if (next == null || next.getScore() > now) {
					try {
						sleep(50);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
					continue;
				}

				/* 获取下次缓存的调度时间 */
				String rowId = next.getElement();
				double delay = conn.zscore("delay:", rowId);
				/* 小于等于0不需要缓存，并删除 */
				if (delay <= 0) {
					conn.zrem("delay:", rowId);
					conn.zrem("schedule:", rowId);
					conn.del("inv:" + rowId);
					continue;
				}
				/* 读取缓存行 */
				Inventory row = Inventory.get(rowId);
				/* 更新调度时间并设置缓存值 */
				conn.zadd("schedule:", now + delay, rowId);
				conn.set("inv:" + rowId, gson.toJson(row));
			}
		}
	}

	public static class Inventory {
		@SuppressWarnings("unused")
		private String id;
		@SuppressWarnings("unused")
		private String data;
		@SuppressWarnings("unused")
		private long time;

		private Inventory(String id) {
			this.id = id;
			this.data = "data to cache...";
			this.time = System.currentTimeMillis() / 1000;
		}

		public static Inventory get(String id) {
			return new Inventory(id);
		}
	}
}
