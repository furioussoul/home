package concurrency;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by szj on 2016/7/9.
 */
public class ShutDown {

    public static class IOBlock implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println("----");
                System.in.read();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("interrupt");
        }
    }

    @Test
    public void main() throws InterruptedException, IOException {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new IOBlock());
        exec.shutdownNow();
        System.in.close();
        TimeUnit.MILLISECONDS.sleep(100000);
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
    }
}
