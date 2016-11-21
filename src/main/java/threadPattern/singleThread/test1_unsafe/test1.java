package threadPattern.singleThread.test1;

/**
 * Created by yanghao-012 on 2016/11/15.
 */
public class test1 {
        public static void main(String[] args) {
            System.out.println("Testing Gate, hit CTRL+C to exit.");
            Gate gate = new Gate();
            new UserThread(gate, "Alice", "Alaska").start();
            new UserThread(gate, "Bobby", "Brazil").start();
            new UserThread(gate, "Chris", "Canada").start();
        }

}
