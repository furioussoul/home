package domain;

import annotation.Aop;
import annotation.Component;
import annotation.Injection;
import aop.interceptor.MyInterceptor;

/**
 * Created by 孙证杰 on 2017/8/1.
 */
@Aop(declaredMethodName = "fly",interceptor = MyInterceptor.class)
@Component
public class FlyBird implements Bird{

    @Injection("flybird")
    public String name;

    @Override
    public String fly(){
        System.out.println(this.name + " is flying");
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
