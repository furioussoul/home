package concurrency;

import org.junit.Test;
import utils.SystemPrinter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by szj on 2016/7/9.
 */
public class WaitNotify {

    @Test
    public void main() throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }

    public static class Car {
        private boolean waxOn = false;

        public synchronized void waxed() {
            waxOn = true;
            notify();
        }

        public synchronized void buffed() {
            waxOn = false;
            notify();
        }

        public synchronized void waitForWaxing() throws InterruptedException {
            while (waxOn == false) {
                wait();
            }
        }

        public synchronized void waitForBuffing() throws InterruptedException {
            while (waxOn == true) {
                wait();
            }
        }
    }

    public static class WaxOn implements Runnable {
        private Car car;

        public WaxOn(Car c) {
            this.car = c;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    SystemPrinter.println("Wax on!");
                    TimeUnit.MILLISECONDS.sleep(200);
                    car.waxed();
                    car.waitForBuffing();
                }
            } catch (InterruptedException e) {
                SystemPrinter.println("Exiting via interrupt");
            }
            SystemPrinter.println("Ending Wax on task");
        }
    }

    public static class WaxOff implements Runnable {
        private Car car;

        public WaxOff(Car c) {
            car = c;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    car.waitForWaxing();
                    SystemPrinter.println("wax off !");
                    TimeUnit.MILLISECONDS.sleep(200);
                    car.buffed();
                }
            } catch (InterruptedException e) {
                SystemPrinter.println("Exiting via interrupt");
            }
            SystemPrinter.println("Ending wax off task");
        }
    }
}
