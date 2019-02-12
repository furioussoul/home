package concurrency;

import java.util.Random;

/**
 * Created by szj on 2016/7/9.
 */
public class Count {
    private int count = 0;
    private Random random = new Random(47);

    public synchronized int increment() {
        int temp = count;
        if (random.nextBoolean() == true) {
            Thread.yield();
        }
        return (count = ++temp);
    }

    public synchronized int value() {
        return count;
    }
}
