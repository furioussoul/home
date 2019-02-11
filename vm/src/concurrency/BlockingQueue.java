package concurrency;

import org.junit.Test;
import utils.SystemPrinter;

import java.util.concurrent.*;

/**
 * Created by szj on 2016/7/10.
 */
public class BlockingQueue {

    private BlockingDeque<Cook> cQ = new LinkedBlockingDeque<>();
    private BlockingDeque<Eat> eQ = new LinkedBlockingDeque<>();

    public class Cook implements Runnable {

        public Cook(){

        }
        public  Cook(Cook c) throws InterruptedException {
            cQ.put(c);
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    eQ.take();
                    TimeUnit.MILLISECONDS.sleep(100);
                    cQ.put(new Cook());
                    SystemPrinter.println("cooked !");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class Eat implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    cQ.take();//wait if null
                    TimeUnit.MILLISECONDS.sleep(100);
                    eQ.put(new Eat());
                    SystemPrinter.println("eated !");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void mian() throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Cook(new Cook()));
        exec.execute(new Eat());
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }
}
