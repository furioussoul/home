package designPattern.adapter;

/**
 * Created by soul on 2016/7/15.
 */
public class UdpControllerAdapter implements Adapter {
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof UdpController);
    }

    @Override
    public void handle(Object controller) {
        ((UdpController) controller).controll();
    }
}
