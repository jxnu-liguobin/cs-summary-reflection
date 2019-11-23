package cn.edu.jxnu.examples.sql;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * 通过动态代理构造一个Connection
 * 该Connection仅仅实现在commit()方法调用时休眠100毫秒
 * @author 梦境迷离
 * @time. 2017-7-27 下午2:52:15  
 * @version V1.0
 */
public class ConnectionDriver {
	static class ConnectionHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("commit")) {
				TimeUnit.MILLISECONDS.sleep(100);
			}

			return null;
		}

	}

	/**
	 * 创建一个Connection的代理，在commit时休眠100毫秒
	 * @time. 2018年4月10日 下午7:17:14
	 * @version V1.0
	 * @return  一个数据库链接
	 */
	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
				new Class<?>[] { Connection.class }, new ConnectionHandler());

	}
}