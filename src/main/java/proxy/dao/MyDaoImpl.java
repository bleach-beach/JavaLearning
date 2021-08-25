package proxy.dao;

public class MyDaoImpl implements MyDao {

    public void query(String a,String b) throws Exception{
        System.out.println(a+b);
        System.out.println("MyDaoImpl.query");

    }

    @Override
    public void proxy() throws Exception {
        System.out.println("MyDaoImpl.proxy");
    }


}
