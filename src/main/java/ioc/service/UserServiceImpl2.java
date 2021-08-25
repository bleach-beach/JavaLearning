package ioc.service;


import ioc.dao.UserDao;

public class UserServiceImpl2 implements UserService {

    UserDao dao;


    public UserServiceImpl2 (UserDao dao){
        this.dao = dao;
    }
    @Override
    public void find() {
        System.out.println("service2");
        dao.query();
    }

    //public void setDao(UserDao dao) {
       // this.dao = dao;
   // }
}
