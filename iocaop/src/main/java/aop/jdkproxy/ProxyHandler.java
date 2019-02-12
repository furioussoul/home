package aop.jdkproxy;

import annotation.Aop;
import aop.interceptor.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理处理类，拦截代理类的方法
 */
public class ProxyHandler implements InvocationHandler {

    private Object target;

    /**
     * @param obj 被代理的类
     */
    public ProxyHandler(Object obj) {
        this.target = obj;
    }

    public Object getTarget() {
        return this.target;
    }

    /**
     * 调用target的方法时会调用invoke方法扩展原方法，和js的fn.apply(context,args[])一样
     *
     * @param proxy  代理后的对象
     * @param method 代理对象被调用的方法
     * @param args   代理对象被调用的入参
     * @return 代理对象被调用的方法的回参
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Aop aop = target.getClass().getAnnotation(Aop.class);
        if (null == aop) {
            return method.invoke(target, args);
        }

        String declaredMethodName = aop.declaredMethodName();
        if (!method.getName().equals(declaredMethodName)) {
            return method.invoke(target, args);
        }

        Class interceptorClazz = aop.interceptor();
        Object interceptor = interceptorClazz.newInstance();
        if (!(interceptor instanceof Interceptor)) {
            throw new RuntimeException("拦截器必须继承Interceptor接口");
        }
        return ((Interceptor) interceptor).intercept(target, method, args);
    }
}
