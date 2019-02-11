package designPattern.factory;

/**
 * Created by soul on 2016/7/18.
 */
public abstract class AbstractFactory {
    private Class clazz;
    public AbstractFactory(Class clazz){
        this.clazz = clazz;
    }

    protected void initClazz(){
        System.out.println("---initing clazz---");
    }

    public Object getBean() throws IllegalAccessException, InstantiationException {
        initClazz();
        return this.clazz.newInstance();
    }
}
