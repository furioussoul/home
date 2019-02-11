package ioc.xml;

/**
 * 获取bean定义的reader
 */
public interface BeanDefinitionReader {

	/**
	 * 加载bean定义到内存
	 * @param location bean的地址
	 * @throws Exception
	 */
	void loadBeanDefinitions(String location) throws Exception;
}
