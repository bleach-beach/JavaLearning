package proxy.test;


import proxy.dao.MyDao;
import proxy.dao.MyDaoImpl;
import proxy.proxy.ProxyUtil2;
import proxy.util.TestCustomHandler;


public class TestCustom {
    public static void main(String[] args) {

        MyDao proxy = null;
        try {
            proxy = (MyDao) ProxyUtil2.newInstance(Class.forName("proxy.dao.MyDao"),new TestCustomHandler(new MyDaoImpl()));
            proxy.query("1","xx");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
