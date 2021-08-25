package proxy.dao;

import java.lang.reflect.Method;

public interface CoustomInvocationHandler {
    public Object invoke(Method method);

    public Object invoke(Method method, Object... args);
}
