package designPattern.adapter;

/**
 * Created by soul on 2016/7/15.
 */
public class HttpControllerAdapter implements Adapter {
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof HttpController);
    }

    @Override
    public void handle(Object controller) {
        ((HttpController) controller).controll();
    }
}
