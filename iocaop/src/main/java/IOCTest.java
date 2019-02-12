import app.AnnotationApplicationContext;
import app.ApplicationContext;
import app.ClassPathXmlApplicationContext;
import domain.Bird;
import domain.Person;

/**
 * 使用IOC容器可以让使用者不用关注类的实例化和之间的依赖关系，从容器中获取bean即可
 * Created by 孙证杰 on 2017/7/31.
 */
public class IOCTest {

    /**
     * 通过jdk的DocumentBuilderFactory解析xml
     * 模拟spring xml配置实体
     * xml在resources下:ioc-aop/ioc.xml
     * 实现了实体的依赖注入和string类型注入
     */
    public static void tesXML() throws Exception {
        String xmlLocation = "ioc-aop/ioc.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlLocation);
        Person p = (Person) applicationContext.getBean("person");
        p.say();
    }

    /**
     * 通过classloader扫描项目，并用ioc流读取文件
     * 模拟spring 注解配置实体
     * 只支持自定义注解Component
     * 实现了注解注入String类型属性
     */
    public static void tesAnnotation() throws Exception {
        String packageLocation = "domain";
        ApplicationContext applicationContext = new AnnotationApplicationContext(packageLocation);
        Bird b = (Bird) applicationContext.getBean("WalkBird");
        b.fly();
    }

    /**
     * spring支持两种aop，jdk动态代理和cglib
     * 这里通过jdk动态代理
     * 模拟spring aop
     * 只支持自定义注解Aop,Aop可设置切面（只支持拦截方法），拦截器
     */
    public static void tesAop() throws Exception {
        String packageLocation = "domain";
        ApplicationContext applicationContext = new AnnotationApplicationContext(packageLocation);
        Bird b = (Bird) applicationContext.getBean("FlyBird");
        b.fly();
    }

    public static void main(String[] arg) throws Exception {
        tesXML();
        tesAnnotation();
        tesAop();
    }
}
