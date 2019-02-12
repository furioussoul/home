package domain;

import annotation.Component;
import annotation.Injection;

/**
 * Created by 孙证杰 on 2017/8/2.
 */
@Component
public class WalkBird implements Bird {
    @Injection("walkBird")
    public String name;

    @Override
    public String fly() {
        System.out.println(this.name + " is flying");
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
