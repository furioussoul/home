package aop.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class YourInterceptor implements Interceptor {

    private void before(Object target, Method method, Object[] args) {
        System.out.println("YourInterceptor before method invoke!");
    }

    private void after(Object target, Method method, Object[] args) {
        System.out.println("YourInterceptor after method invoke!");
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args) {
        Object result = null;
        before(target, method, args);
        try {
            result = method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        after(target, method, args);
        return result;
    }
}
