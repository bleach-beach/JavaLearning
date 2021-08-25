package ioc.test;

import ioc.org.spring.util.AnnotationConfigApplicationContext;

public class TestAnno {
    public static void main(String[] args) {
//        BeanFactory beanFactory = new BeanFactory("spring.xml");
//
//        UserService service = (UserService) beanFactory.getBean("service");

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new
                AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.scan("ioc.service");

       // service.find();
    }
}
