package app;

import factory.ClassPathXmlBeanFactory;

/**
 * 扫描项目根目录（classpath）下的xml的IOC容器（上下文）,
 * classpath下xml配置的bean会实例化到这个上下文
 * Created by 孙证杰 on 2017/8/1.
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext{
    public ClassPathXmlApplicationContext(String xmlLocation) {
        this.beanFactory = new ClassPathXmlBeanFactory(xmlLocation);
    }
}
