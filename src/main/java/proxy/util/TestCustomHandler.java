package proxy.util;



import proxy.dao.CoustomInvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestCustomHandler implements CoustomInvocationHandler {
    Object target;
    public TestCustomHandler(Object target){
        this.target=target;
    }

    @Override
    public Object invoke(Method method) {
        Object o = null;
        try {
            System.out.println("----------------");
            o = method.invoke(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Object invoke(Method method, Object... args) {
        Object o = null;
        try {
            System.out.println("*----------------*");
            o = method.invoke(target,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }
}
