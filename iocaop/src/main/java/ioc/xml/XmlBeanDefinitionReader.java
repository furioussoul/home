package ioc.xml;

import bean.BeanDefinition;
import bean.PropertyValue;
import injection.BeanReference;
import io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * 获取存储在xml中的bean定义的reader
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{

	public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		InputStream inputStream = this.getResourceLoader().getResource(location).getInputStream();
		doLoadBeanDefinitions(inputStream);
	}

	private void doLoadBeanDefinitions(InputStream inputStream) throws Exception{
		//把xml转成文档流
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(inputStream);
		registerBeanDefinitions(doc);
		inputStream.close();
	}

	/**
	 * 把文档流中的bean定义注册到registry
	 * @param document 文档流
	 */
	private void registerBeanDefinitions(Document document){
		Element root = document.getDocumentElement();
		parseBeanDefinitions(root);
	}

	/**
	 * 从根节点开始遍历文档流
	 * @param root 文档流的根
	 */
	private void parseBeanDefinitions(Element root){
		NodeList nl = root.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node node = nl.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}	
	}

	/**
	 * 从文档流的节点中提取bean属性组装成BeanDefinition对象，并注册到registry
	 * @param ele 文档流的节点
	 */
	private void processBeanDefinition(Element ele){
		String id = ele.getAttribute("id");
		String className = ele.getAttribute("class");
		BeanDefinition beanDefinition = new BeanDefinition();
		processProperty(ele, beanDefinition);
		beanDefinition.setBeanClassName(className);
		this.getRegistry().put(id, beanDefinition);
	}

	/**
	 * 拼装bean的属性对象
	 * @param ele 文档流的节点
	 * @param beanDefinition bean属性对象
	 */
	private void processProperty(Element ele, BeanDefinition beanDefinition){
		NodeList propertyNode = ele.getElementsByTagName("property");
		for(int i = 0; i < propertyNode.getLength(); i++){
			Node node = propertyNode.item(i);
			if(node instanceof Element){
				Element propertyEle = (Element) node;
				String name  = propertyEle.getAttribute("name");
				String value = propertyEle.getAttribute("value");
				if(value != null && value.length() > 0){
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				}else{
					String ref = propertyEle.getAttribute("ref");
					if(ref == null || ref.length() == 0){
						throw new IllegalArgumentException("u must specify a ref or value");
					}
					BeanReference beanReference = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				}
			}
		}
	}
}
