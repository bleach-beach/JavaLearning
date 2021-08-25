package ioc.test;


import ioc.org.spring.util.BeanFactory;
import ioc.service.UserService;

public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory("spring.xml");

        UserService service2 = (UserService) beanFactory.getBean("service2");
        UserService service = (UserService) beanFactory.getBean("service");
        UserService service3 = (UserService) beanFactory.getBean("service3");

        service.find();
        service2.find();;
        service3.find();
    }
}
