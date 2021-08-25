package proxy.test;

import proxy.dao.MyDao;
import proxy.dao.MyDaoImpl;
import proxy.util.MyInvocationHandler;
import proxy.util.TestCustomHandler;

import java.lang.reflect.Proxy;

/**
 * @program: javaExercise
 * @description:
 * @author: admin
 * @create: 2019/6/22 14:44
 */
public class TestJdkProxy {
    public static void main(String[] args) {
                  //jdk proxy
        MyDao proxy = (MyDao) Proxy.newProxyInstance(Test.class.getClassLoader(),new Class[]{MyDao.class},new MyInvocationHandler(new MyDaoImpl()));
        try {
            proxy.proxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
