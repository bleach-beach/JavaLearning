package ioc.dao;

public class UserDaoImpl implements UserDao {
    @Override
    public void query() {
        System.out.println("dao");
    }
}
