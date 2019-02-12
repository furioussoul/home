package factory;

import bean.BeanDefinition;
import ioc.lifecirycle.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory {

    protected final List<String> beanDefinitionNames = new ArrayList<>();
    protected final Map<String, Object> iocContainer = new ConcurrentHashMap<>();
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    protected BeanPostProcessor beanPostProcessor = new BeanPostProcessor();

    @Override
    public void registerBean(String name, Object bean) {
        this.iocContainer.put(name, bean);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }
}
