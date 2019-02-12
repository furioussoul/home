package designPattern.adapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soul on 2016/7/15.
 */
public class Main {

    private static List<Adapter> adapters = new ArrayList<>();

    static {
        adapters.add(new TcpControllerAdapter());
        adapters.add(new UdpControllerAdapter());
        adapters.add(new HttpControllerAdapter());
    }

    public void doControll(Object controller) {
        for (Adapter adapter : adapters) {
            if (adapter.supports(controller)) {
                adapter.handle(controller);
                return;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Test
    public void test0() {
        doControll(new HttpController());
    }


    /**
     * 以下不使用适配器
     **/
    @Test
    public void test1() {
        doControll(new HttpController());
    }

    public void doCon(Object controller) {
        if (controller instanceof HttpController) {
            ((HttpController) controller).controll();
        } else if (controller instanceof UdpController) {
            ((UdpController) controller).controll();
        } else if (controller instanceof TcpController) {
            ((TcpController) controller).controll();
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
