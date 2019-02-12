package factory;

import annotation.Component;
import bean.BeanDefinition;
import ioc.util.PkgScanner;

import java.io.IOException;
import java.util.List;

/**
 * Created by 孙证杰 on 2017/8/1.
 */
public class AnnotationBeanFactory extends AbstractBeanFactory {
    /**
     * @param packageLocation 定义bean的包路径,扫描特定注解并实例化
     */
    public AnnotationBeanFactory(String packageLocation) {
        PkgScanner scanner = new PkgScanner(packageLocation, Component.class);
        List<String> clazzUrlList = null;
        try {
            clazzUrlList = scanner.scan();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (clazzUrlList != null) {
            for (String clazz : clazzUrlList) {
                try {
                    Object bean = Class.forName(clazz).newInstance();
                    Object proxy = beanPostProcessor.postProcessAfterInitialization(bean);
                    this.registerBean(bean.getClass().getSimpleName(), proxy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Object getBean(String name) throws Exception {
        if (iocContainer.containsKey(name)) {
            return iocContainer.get(name);
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        Class clazz = beanDefinition.getBeanClass();
        Object object = clazz.newInstance();
        iocContainer.put(name, object);
        return object;
    }
}
