package proxy.test;


import proxy.dao.MyDao;
import proxy.dao.MyDaoImpl;
import proxy.util.MyInvocationHandler;
import sun.misc.ProxyGenerator;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;


public class Test {
    public static void main(String[] args) {
//          //自定义
//        MyDao proxy = (MyDao) ProxyUtil.newInstance(MyDao.class,new TestCustomHandler(new MyDaoImpl()));
//        try {
//            proxy.proxy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        byte[] bytes=ProxyGenerator.generateProxyClass("$Proxy18",new Class[]{MyDao.class});

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\$Proxy18.class");
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        System.out.println(proxy.proxy());
        MyDao jdkproxy = (MyDao) Proxy.newProxyInstance(Test.class.getClassLoader(),
                new Class[]{MyDao.class},new MyInvocationHandler(new MyDaoImpl()));

        //jdkproxy.query();
        try {
            jdkproxy.proxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
