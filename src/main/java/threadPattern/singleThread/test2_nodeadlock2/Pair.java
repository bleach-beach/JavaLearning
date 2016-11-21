package threadPattern.singleThread.test2_nodeadlock2;


//新建个类放置SPOON,FORK。打破有多个共享资源的死锁条件
public class Pair {
    private final Tool lefthand;
    private final Tool righthand;
    public Pair(Tool lefthand, Tool righthand) {
        this.lefthand = lefthand;
        this.righthand = righthand;
    }
    public String toString() {
        return "[ " + lefthand + " and " + righthand + " ]";
    }
}
