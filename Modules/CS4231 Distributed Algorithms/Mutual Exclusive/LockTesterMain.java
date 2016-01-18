package MutualExclusive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by RayZK on 17/01/16.
 */

class MyThread implements Runnable {

    private int myid;
    volatile public static int[] counter;
    public Lock lock;

    // Constructor
    public MyThread(int id, int[] counter, Lock lock) {
        myid = id;
        this.counter = counter;
        this.lock = lock;
    }

    private void increaseCounter() {
        System.out.println("#" + myid + ": increase counter");
        counter[0]++;
    }

    private void decreaseCounter() {
        System.out.println("#" + myid + ": decrease counter");
        counter[0]--;
    }

    private void checkCounter() {
        System.out.println("#" + myid + ": counter is " + counter[0]);
        if (counter[0] > 1) {
            System.out.println("#" + myid + " *************** Error! ****************");
        }

    }

    @Override
    public void run() {
        while (true) {
            checkCounter();
            lock.RequestCS(myid);
            checkCounter();
            increaseCounter();
            Thread.yield();
            checkCounter();
            decreaseCounter();
            lock.ReleaseCS(myid);
            Thread.yield();
        }
    }
}


public class LockTesterMain {

    public static volatile int[] counter = {0};
    public static int numberOfThreads = 10;

    public volatile static Lock peterson;
    public volatile static Lock multiPeterson;
    public volatile static Lock bakery;
    public volatile static Lock multiPetersonTreeLock;

    public static void main(String[] args) {
        peterson = new PetersonLock();
        multiPeterson = new MultiPetersonLock(numberOfThreads);
        bakery = new BakeryLock(numberOfThreads);
        multiPetersonTreeLock = new MultiPetersonTreeLock(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new Thread(new MyThread(i, counter, multiPeterson));
            t.start();
        }

//        ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);
//        for (int i = 0; i < numberOfThreads; i++)
//            exec.execute(new MyThread(i, counter, multiPeterson));
//        exec.shutdown();
    }
}
