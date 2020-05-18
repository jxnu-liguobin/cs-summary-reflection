package cn.edu.jxnu.examples.proxy;

/**
 * 拦截接口
 *
 * @author 梦境迷离
 */
public interface Interceptor {
    Object intercept(HandlerInvocation invocation) throws Exception;
}
