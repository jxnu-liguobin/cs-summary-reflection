package cn.edu.jxnu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ZParams;

import java.util.*;

/**
 * @description 第一章java版例题 redis 2.6
 * @author Mr.Li
 * 
 */
public class RedisDemo1 {
	/* 200票 一天有864400秒 即每票需要432分 */

	private static final int ONE_WEEK_IN_SECONDS = 7 * 86400;
	private static final int VOTE_SCORE = 432;
	private static final int ARTICLES_PER_PAGE = 25;

	public static final void main(String[] args) {
		new RedisDemo1().run();
	}

	/**
	 * @DESCRIPTION 构造数据
	 */
	public void run() {
		/* 链接redis */

		Jedis conn = new Jedis("localhost");
		System.out.println("success start !");
		/*
		 * conn.select(15); 写文章
		 */

		String articleId = postArticle(conn, "username", "A title",
				"http://www.google.com");
		System.out.println("发布一篇新文章ID是: " + articleId);
		System.out.println("文章信息是:");
		Map<String, String> articleData = conn.hgetAll("article:" + articleId);
		for (Map.Entry<String, String> entry : articleData.entrySet()) {
			System.out.println("  " + entry.getKey() + ": " + entry.getValue());
		}

		System.out.println();
		/* 文章投票 */

		articleVote(conn, "other_user", "article:" + articleId);
		String votes = conn.hget("article:" + articleId, "votes");
		System.out.println("对文章进行了一次投票，现在票数是: " + votes);
		assert Integer.parseInt(votes) > 1;

		System.out.println("以分数排序的文章是:");
		List<Map<String, String>> articles = getArticles(conn, 1);
		printArticles(articles);
		assert articles.size() >= 1;
		/* 添加文章分组 */

		addGroups(conn, articleId, new String[] { "new-group" });
		System.out.println("添加文章到分组, 其他文章是:");
		articles = getGroupArticles(conn, "new-group", 1);
		printArticles(articles);
		assert articles.size() >= 1;
	}

	/**
	 * @description 发布并获取文章
	 * @param conn
	 * @param user
	 * @param title
	 * @param link
	 * @return
	 */
	public String postArticle(Jedis conn, String user, String title, String link) {
		/* 生成新的文章id */

		String articleId = String.valueOf(conn.incr("article:"));
		/* 将发布者添加到已投票名单，设置过期时间为一周 */

		String voted = "voted:" + articleId;
		conn.sadd(voted, user);
		conn.expire(voted, ONE_WEEK_IN_SECONDS);
		/* 将文章存到散列 */

		long now = System.currentTimeMillis() / 1000;
		String article = "article:" + articleId;
		HashMap<String, String> articleData = new HashMap<String, String>();
		articleData.put("title", title);
		articleData.put("link", link);
		articleData.put("user", user);
		articleData.put("now", String.valueOf(now));
		articleData.put("votes", "1");
		conn.hmset(article, articleData);
		/* 添加到时间和分数的有序集合 */
		/* 哈希是先值后键 */

		conn.zadd("score:", now + VOTE_SCORE, article);
		conn.zadd("time:", now, article);

		return articleId;
	}

	/**
	 * @description 文章投票
	 * @param conn
	 * @param user
	 * @param article
	 */
	public void articleVote(Jedis conn, String user, String article) {
		/* 计算截止时间 */

		long cutoff = (System.currentTimeMillis() / 1000) - ONE_WEEK_IN_SECONDS;
		/* 检查是否可以投票 ：发布超过一周就不能投票了 */

		if (conn.zscore("time:", article) < cutoff) {
			return;
		}
		/* 如果是第一次投票则添加成功 */

		String articleId = article.substring(article.indexOf(':') + 1);
		if (conn.sadd("voted:" + articleId, user) == 1) {
			/* 对分数和文章投票数进行修改 */

			conn.zincrby("score:", VOTE_SCORE, article);
			conn.hincrBy(article, "votes", 1l);
		}
	}

	/**
	 * @description 获取文章 设置默认值
	 * @param conn
	 * @param page
	 * @return
	 */
	public List<Map<String, String>> getArticles(Jedis conn, int page) {
		/* 设置默认值 */

		return getArticles(conn, page, "score:");
	}

	/**
	 * @description 获取文章：支持排序
	 * @param conn
	 * @param page
	 * @param order
	 * @return
	 */
	public List<Map<String, String>> getArticles(Jedis conn, int page,
			String order) {
		int start = (page - 1) * ARTICLES_PER_PAGE;
		int end = start + ARTICLES_PER_PAGE - 1;
		/* 以分值大到小排序并取出来 */

		Set<String> ids = conn.zrevrange(order, start, end);
		List<Map<String, String>> articles = new ArrayList<Map<String, String>>();
		for (String id : ids) {
			Map<String, String> articleData = conn.hgetAll(id);
			articleData.put("id", id);
			articles.add(articleData);
		}

		return articles;
	}

	/**
	 * @description 添加文章分组，一篇文章可以属于多个分组
	 * @param conn
	 * @param articleId
	 * @param toAdd
	 */
	public void addGroups(Jedis conn, String articleId, String[] toAdd) {
		String article = "article:" + articleId;
		for (String group : toAdd) {
			conn.sadd("group:" + group, article);
		}
	}

	/**
	 * @description 得到分组中的文章 设置默认值
	 * @param conn
	 * @param group
	 * @param page
	 * @return
	 */
	public List<Map<String, String>> getGroupArticles(Jedis conn, String group,
			int page) {
		return getGroupArticles(conn, group, page, "score:");
	}

	/**
	 * @description 得到分组中的文章：排序
	 * @param conn
	 * @param group
	 * @param page
	 * @param order
	 * @return
	 */
	public List<Map<String, String>> getGroupArticles(Jedis conn, String group,
			int page, String order) {
		String key = order + group;
		/* 检查是否有已缓存的结果，如果没有就排序 */

		if (!conn.exists(key)) {
			/* 根据发布时间和评分进行排序 */

			ZParams params;
			params = new ZParams().aggregate(ZParams.Aggregate.MAX);
			/* 进行稽核交集运算 存入key集合 */

			conn.zinterstore(key, params, "group:" + group, order);
			/* 60s后自动删除 */

			conn.expire(key, 60);
		}
		return getArticles(conn, page, key);
	}

	/**
	 * @description 输出文章信息
	 * @param articles
	 */
	private void printArticles(List<Map<String, String>> articles) {
		for (Map<String, String> article : articles) {
			System.out.println("  id: " + article.get("id"));
			for (Map.Entry<String, String> entry : article.entrySet()) {
				if (entry.getKey().equals("id")) {
					continue;
				}
				System.out.println("    " + entry.getKey() + ": "
						+ entry.getValue());
			}
		}
	}
}
