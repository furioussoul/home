package factory;

import bean.BeanDefinition;
import bean.PropertyValue;
import bean.PropertyValues;
import injection.BeanReference;
import io.ResourceLoader;
import ioc.xml.XmlBeanDefinitionReader;

import java.lang.reflect.Field;
import java.util.Map;

public class ClassPathXmlBeanFactory extends AbstractBeanFactory {
    /**
     * @param location 定义bean的xml的路径
     */
    public ClassPathXmlBeanFactory(String location) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(new ResourceLoader());
        try {
            reader.loadBeanDefinitions(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, BeanDefinition> beanDefinitionMap : reader.getRegistry().entrySet()) {
            this.registerBeanDefinition(beanDefinitionMap.getKey(), beanDefinitionMap.getValue());
        }
    }

    @Override
    public Object getBean(String name) throws Exception {
        if (iocContainer.containsKey(name)) {
            return iocContainer.get(name);
        }
        Object object = create(name);
        Object proxy = beanPostProcessor.postProcessAfterInitialization(object);
        iocContainer.put(name, proxy);
        return object;
    }

    private Object create(String name) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        Class clazz = beanDefinition.getBeanClass();
        Object object = null;
        try {
            object = clazz.newInstance();
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                Field field = clazz.getField(propertyValue.getName());
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (propertyValue.getValue() instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) propertyValue.getValue();
                    String ref = beanReference.getRef();
                    Object refBean = getBean(ref);
                    field.set(object, refBean);
                } else {
                    field.set(object, propertyValue.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
