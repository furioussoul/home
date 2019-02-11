package designPattern.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by soul on 2016/7/15.
 */
public class TcpControllerAdapter implements Adapter{
    @Override
    public boolean supports(Object controller){
        return (controller instanceof TcpController);
    }

    @Override
    public void handle(Object controller) {
        ((TcpController)controller).controll();
    }
}
