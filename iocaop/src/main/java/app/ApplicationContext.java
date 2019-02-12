package app;

/**
 * Created by 孙证杰 on 2017/8/1.
 * 应用上下文接口
 */
public interface ApplicationContext {
    /**
     * 根据name从IOC容器获取bean实例
     *
     * @param name bean的name
     * @return bean
     * @throws Exception
     */
    Object getBean(String name) throws Exception;
}
