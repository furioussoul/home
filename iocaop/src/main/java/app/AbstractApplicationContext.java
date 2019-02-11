package app;

import factory.BeanFactory;

/**
 * Created by 孙证杰 on 2017/8/1.
 */
public class AbstractApplicationContext implements ApplicationContext{

    protected BeanFactory beanFactory;

    @Override
    public Object getBean(String name) throws Exception {
        return this.beanFactory.getBean(name);
    }
}
