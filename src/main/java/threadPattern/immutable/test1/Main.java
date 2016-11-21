package threadPattern.immutable.test1;

/**
 * Created by yanghao-012 on 2016/11/17.
 * 测试SYNCHRONIZED的性能
 */
public class Main {
    private static final long call_count = 1000000L;
    public static void main(String[] args){
        trial("NotSynch",call_count,new NotSynch());
        trial("Synch",call_count,new Synch());
    }
    private static void trial(String msg,long count,Object obj){
        System.out.println(msg+": Begin");
        long start_time = System.currentTimeMillis();
        for(long i=0;i<count;i++){
            obj.toString();
        }
        System.out.println(msg+": END");
        System.out.println("Elapsedtime=" + (System.currentTimeMillis()-start_time)+"msec.");
    }
}

class NotSynch{
    private final String name = "NotSynch";
    public String toString(){
        return "[" + name + "]";
    }
}

class Synch{
    private final String name = "Synch";
    public synchronized String toString(){
        return "[" + name + "]";
    }
}
