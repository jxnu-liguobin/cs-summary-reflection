package cn.edu.jxnu.examples.proxy;

/**
 * 拦截接口的简单实现
 *
 * @author 梦境迷离
 */
public class LoggerInterceptor implements Interceptor {
    public Object intercept(HandlerInvocation invocation) throws Exception {
        System.out.println("pre handle");
        Object result = invocation.invoke();
        System.out.println("post handle");
        return result;
    }
}