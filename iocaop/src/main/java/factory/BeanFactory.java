package factory;

import bean.BeanDefinition;

/**
 * bean制造工厂
 */
public interface BeanFactory {
	/**
	 * 从工厂根据name获取bean实例
	 * @param name bean的name
	 * @return bean
	 * @throws Exception
	 */
	Object getBean(String name) throws Exception;

	void registerBean(String name, Object bean);

	/**
	 * 将bean属性对象注册到工厂
	 * @param name bean的name
	 * @param beanDefinition bean的属性对象
	 */
	void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
