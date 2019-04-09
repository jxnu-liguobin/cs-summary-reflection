package cn.edu.jxnu.proxy;

/**
 * @description 拦截类
 * @author Mr.Li
 *
 */
public interface Interceptor {
	public Object intercept(HandlerInvocation invocation) throws Exception;
}