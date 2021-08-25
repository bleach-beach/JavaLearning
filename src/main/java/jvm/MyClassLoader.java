package jvm;

import sun.misc.PerfCounter;
import sun.misc.Resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

public class MyClassLoader extends ClassLoader {

    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    private byte[] loadByte(String name) throws IOException {
        name = name.replaceAll("\\.","/");
        FileInputStream fileInputStream = new FileInputStream(classPath+"/"+name+".class");
        int len = fileInputStream.available();
        byte[] data = new byte[len];
        fileInputStream.read(data);
        fileInputStream.close();
        return data;
    }

    protected Class<?> findClass(final String name)
    {
        byte[] data = new byte[0];
        try {
            data = loadByte(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //类加载
        return defineClass(name,data,0,data.length);
    }


    //重写打破双亲委派机制
    protected Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {

        long t1 = System.nanoTime();
        synchronized(this.getClassLoadingLock(name)) {
            Class c = this.findLoadedClass(name);
            if (c == null) {

                if(!name.startsWith("jvm")){
                    c = this.getParent().loadClass(name);
                }else{
                    c = findClass(name);
                }

                long t2 = System.nanoTime();
                PerfCounter.getParentDelegationTime().addTime(t2 - t1);
                PerfCounter.getFindClassTime().addElapsedTimeFrom(t2);
                PerfCounter.getFindClasses().increment();
                }


            if (resolve) {
                this.resolveClass(c);
            }

            return c;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        MyClassLoader classLoader = new MyClassLoader("E:/技术/TEST");

        Class clazz = classLoader.loadClass("jvm.loadClassDemo");

        Object obj = clazz.newInstance();

        Method method = clazz.getDeclaredMethod("sout",null);

        method.invoke(obj,null);

        System.out.println(clazz.getClassLoader().getClass().getName());
    }


}
