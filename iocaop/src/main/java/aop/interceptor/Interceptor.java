package aop.interceptor;

import java.lang.reflect.Method;

/**
 * Created by 孙证杰 on 2017/8/1.
 */
public interface Interceptor {
    Object intercept(Object target, Method method, Object[] args);
}
