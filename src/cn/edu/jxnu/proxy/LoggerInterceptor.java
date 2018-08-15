package cn.edu.jxnu.proxy;

//LoggerInterceptor实现参考：
public class LoggerInterceptor implements Interceptor {
	public Object intercept(HandlerInvocation invocation) throws Exception {
		System.out.println("pre handle");
		Object result = invocation.invoke();
		System.out.println("post handle");
		return result;
	}
}