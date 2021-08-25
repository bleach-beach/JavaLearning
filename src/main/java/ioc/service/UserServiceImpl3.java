package ioc.service;


import ioc.dao.UserDao;

public class UserServiceImpl3 implements UserService {

    UserDao dao;


    @Override
    public void find() {
        System.out.println("service3");
        dao.query();
    }

    public void setDao(UserDao dao) {
        this.dao = dao;
    }
}
