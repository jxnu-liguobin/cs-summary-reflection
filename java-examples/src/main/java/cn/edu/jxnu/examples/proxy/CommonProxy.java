package cn.edu.jxnu.examples.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * JDK动态代理实现日志拦截
 *
 * @author 梦境迷离
 */
public class CommonProxy implements InvocationHandler {
    private static List<Interceptor> interceptorList = new ArrayList<>();
    private Object target;

    public static void initInterceptors(List<Interceptor> list) {
        interceptorList.addAll(list);
    }

    public CommonProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HandlerInvocation handlerInvocation =
                new HandlerInvocation(target, method, args, interceptorList.iterator());
        return handlerInvocation.invoke();
    }

    public static void main(String[] args) {
        List<Interceptor> list = new ArrayList<>();
        list.add(new LoggerInterceptor());
        CommonProxy.initInterceptors(list);

        MyBusinessImpl myBusinessImpl = new MyBusinessImpl();
        MyBusiness myBusiness =
                (MyBusiness)
                        Proxy.newProxyInstance(
                                MyBusiness.class.getClassLoader(),
                                new Class<?>[] {MyBusiness.class},
                                new CommonProxy(myBusinessImpl));
        myBusiness.xxx1("aaa");
        myBusiness.xxx2(123);
    }

    // ===========================测试类======================================
    public interface MyBusiness {
        void xxx1(String msg);

        void xxx2(int value);
    }

    static class MyBusinessImpl implements MyBusiness {
        public void xxx1(String msg) {
            System.out.println(msg);
        }

        public void xxx2(int value) {
            System.out.println(value);
        }
    }
}
