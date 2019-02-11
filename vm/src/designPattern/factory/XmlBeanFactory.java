package designPattern.factory;

/**
 * Created by soul on 2016/7/18.
 */
public class XmlBeanFactory extends AbstractFactory {
    public XmlBeanFactory(Class clazz) {
        super(clazz);
    }

    @Override
    protected void initClazz() {
        System.out.println("---XmlBeanFactory---");
    }
}
