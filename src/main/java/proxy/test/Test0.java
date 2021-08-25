package proxy.test;


import proxy.dao.MyDao;
import proxy.dao.MyDaoImpl;
import proxy.proxy.ProxyUtil;
import proxy.util.MyInvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class Test0 {
    public static void main(String[] args) throws Exception {
       // Method method = Class.forName("proxy.dao.MyDao").getDeclaredMethod("query",String.class,String.class);
        MyDao proxy = (MyDao) ProxyUtil.newInstance(new MyDaoImpl());
        proxy.query("1","xx");

    }
}
