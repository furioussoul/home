package motan;

/**
 * Created by soul on 2016/7/18.
 */
public class FooServiceImpl implements FooService {
    @Override
    public String hello(String name) {
        System.out.println(name + " invoked rpc service");
        return "hello " + name;
    }
}
