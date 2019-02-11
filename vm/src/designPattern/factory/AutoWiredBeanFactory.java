package designPattern.factory;

/**
 * Created by soul on 2016/7/18.
 */
public class AutoWiredBeanFactory extends AbstractFactory{
    public AutoWiredBeanFactory(Class clazz) {
        super(clazz);
    }

    @Override
    protected void initClazz() {
        System.out.println("---AutoWiredBeanFactory---");
    }
}
