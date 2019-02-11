package app;

import factory.AnnotationBeanFactory;

/**
 * 扫描注解的IOC容器,保存注解了的bean到这个上下文
 * Created by 孙证杰 on 2017/8/1.
 */
public class AnnotationApplicationContext extends AbstractApplicationContext{
    public AnnotationApplicationContext(String packageLocation) {
        this.beanFactory = new AnnotationBeanFactory(packageLocation);
    }
}
