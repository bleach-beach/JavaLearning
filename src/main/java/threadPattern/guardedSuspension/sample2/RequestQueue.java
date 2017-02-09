package threadPattern.guardedSuspension.sample2;

import java.util.LinkedList;

public class RequestQueue {
    private final LinkedList queue = new LinkedList();
    public synchronized Request getRequest() {
        //若此处换成IF条件
        /*
        在Java对象中，有两种池
        琐池-----------------------synchronized
        等待池---------------------wait(),notify(),notifyAll()
        如果一个线程调用了某个对象的wait方法，那么该线程进入到该对象的等待池中(并且已经将锁释放)，
        如果未来的某一时刻，另外一个线程调用了相同对象的notify方法或者notifyAll方法，
        那么该等待池中的线程就会被唤起，然后进入到对象的锁池里面去获得该对象的锁，
        如果获得锁成功后，那么该线程就会沿着wait方法之后的路径继续执行。注意是沿着wait方法之后
        */
        //所以当同时有多个线程做此操作时（此例只有SERVER线程），
        //当第一个线程被唤醒执行操作使SIZE为0后，第二个线程因为警戒条件判断过因此即使SIZE为0依然做REMOVE操作
        //使警戒失效
        while (queue.size() <= 0) {
            //若把TRY放到WHILE上，如果其他线程调用INTERRUPT方法，即使guard条件没有满足，也会跳到CATCH中，随后执行后续REMOVE操作
            try {
                System.out.println(Thread.currentThread().getName() + ": wait() begins, queue = " + queue);
                //若只在WAIT上加锁，首先QUEUE为非线程安全，多线程环境由于未加锁竞争SIZE变量，当所有线程被唤醒且size不为0时，若速度够快有可能发生都会执行REMOVE操作。
                //WAIT与SLEEP其中区别是WAIT会释放实例的锁，所以如果此处用SLEEP会造成livelock
                wait();
                System.out.println(Thread.currentThread().getName() + ": wait() ends,   queue = " + queue);
            } catch (InterruptedException e) {      
            }                                       
        }                                           
        return (Request)queue.removeFirst();
    }
    public synchronized void putRequest(Request request) {
        queue.addLast(request);
        System.out.println(Thread.currentThread().getName() + ": notifyAll() begins, queue = " + queue);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + ": notifyAll() ends, queue = " + queue);
    }
}
