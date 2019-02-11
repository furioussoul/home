package ioc.lifecirycle;

import annotation.Aop;
import aop.jdkproxy.ProxyFactory;
import aop.jdkproxy.ProxyHandler;

/**
 * ioc bean实例化生命周期
 */
public class BeanPostProcessor {

	/**
	 * bean实例化之前调用
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
		return null;
	}


	/**
	 * bean实例化之后调用
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Object postProcessAfterInitialization(Object bean) throws Exception {
		injectProperty(bean);
		return proxy(bean);
	}

	private void injectProperty(Object bean){
		AnnotationInjection annotationInjection = new AnnotationInjection();
		annotationInjection.inject(bean);
	}

	private Object proxy(Object bean){
		if(bean.getClass().getAnnotation(Aop.class) != null){
			return ProxyFactory.newInstance(new ProxyHandler(bean));
		}
		return bean;
	}
}
