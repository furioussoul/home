package designPattern.adapter;

/**
 * Created by soul on 2016/7/15.
 */
public interface Adapter {
    boolean supports(Object controller);
    void handle(Object controller);
}
