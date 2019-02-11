package concurrency;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by szj on 2016/7/9.
 */
public class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>();
    private int number = 0;
    private final int id;
    private static volatile boolean canceled = false;
    public static void cancel(){
        canceled = true;
    }

    public Entrance(int id){
        this.id = id;
        entrances.add(this);
    }
    @Override
    public void run() {
        while (!canceled){
            synchronized (this){
                number++;
            }
            System.out.println(this + "Total:" + count.increment());
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("stopping" + this);
    }

    public synchronized int getValue(){
        return this.number;
    }

    public static int getTotalCount(){
        return count.value();
    }

    public static int sumEntrances(){
        int sum = 0;
        for(Entrance entrance : entrances){
            sum += entrance.getValue();
        }
        return sum;
    }

    @Override
    public String toString(){
        return "entrance " + id + " : " + getValue();
    }

    public static void main(String[] ar){
        for(int i = 0; i < 5; i++){
            new Thread(new Entrance(i)).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("closing entrances");
        Entrance.cancel();
        System.out.println("Count :" + Entrance.getTotalCount());
        System.out.println("Entrances :" + Entrance.sumEntrances());
    }
}
