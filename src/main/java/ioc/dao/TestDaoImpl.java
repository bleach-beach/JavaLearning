package ioc.dao;

public class TestDaoImpl implements TestDao {
    @Override
    public void query() {
        System.out.println("test");
    }
}
