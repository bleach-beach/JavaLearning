package threadPattern.singleThread.test3_mutex2;

public final class Mutex {
    private long locks = 0;
    private Thread owner = null;
    public synchronized void lock() {
        Thread me = Thread.currentThread();
        //只能相同线程加锁
        while (locks > 0 && owner != me) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // 表示lock数为0或者自己已经lock
        // locks == 0 || owner == me
        owner = me;
        locks++;
    }
    public synchronized void unlock() {
        Thread me = Thread.currentThread();
        //只能相同线程解锁，或者不可重复解锁
        if (locks == 0 || owner != me) {
            return;
        }
        // 表示lock数大于0并且自己已经锁住
        // locks > 0 && owner == me
        locks--;
        if (locks == 0) {
            owner = null;
            notifyAll();
        }
    }
}
