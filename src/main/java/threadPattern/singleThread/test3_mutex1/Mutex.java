package threadPattern.singleThread.test3_mutex1;


//缺陷1.某线程重复调用LOCK方法会把自己锁在外面
//    2.不用调用LOCK，就可以UNLOCK
public final class Mutex {
    private boolean busy = false;
    public synchronized void lock() {
        while (busy) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        busy = true;
    }
    public synchronized void unlock() {
        busy = false;
        notifyAll();
    }
}
