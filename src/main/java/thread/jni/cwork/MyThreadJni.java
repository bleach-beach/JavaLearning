package thread.jni.cwork;

/**
 * @program: javaExercise
 * @description:
 * @author: admin
 * @create: 2019/7/21 17:26
 */
public class MyThreadJni {

    static{
        System.loadLibrary("MyThreadJniNative");
    }
    public static void main(String[] args) {
        MyThreadJni myThreadJni =new MyThreadJni();
        myThreadJni.start0();

        myThreadJni.run0();
    }
    private native void start0();

    public void run(){
        System.out.println("I am java Thread !! Now is running");
    }

    private native void run0();
}
