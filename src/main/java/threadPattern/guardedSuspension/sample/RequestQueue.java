package threadPattern.guardedSuspension.sample;

import java.util.LinkedList;

public class RequestQueue {
    private final LinkedList queue = new LinkedList();
    public synchronized Request getRequest() {
        while (queue.size() <= 0) {
            try {                                   
                wait();
            } catch (InterruptedException e) {      
            }                                       
        }                                           
        return (Request)queue.removeFirst();
    }
    public synchronized void putRequest(Request request) {
        //把NotifyAll顺序放前面并会导致线程安全问题
        queue.addLast(request);
        notifyAll();
    }
}
