package ioc.xml;

import bean.BeanDefinition;
import io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取bean定义的reader
 */
abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

	/**
	 * 存储bean定义
	 */
	private Map<String, BeanDefinition> registry;

	/**
	 * 获取bean定义的loader
	 */
	private ResourceLoader resourceLoader;

	AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		this.registry = new HashMap<>();
	}
	
	ResourceLoader getResourceLoader(){
		return resourceLoader;
	}
	
	public Map<String,BeanDefinition> getRegistry(){
		return registry;
	}	
}
