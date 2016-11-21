package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by yanghao-012 on 2016/9/19.
 */
public class RpcImporter<S> {
    public S importer(final Class<?> serviceClass, final InetSocketAddress addr){
        //本地接口调用转换成JDK动态代理，实现接口的远程调用
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{serviceClass.getInterfaces()[0]}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectOutputStream output = null;
                ObjectInputStream input = null;
                try{
                    //连接远程服务提供者
                    socket = new Socket();
                    socket.connect(addr);
                    output = new ObjectOutputStream(socket.getOutputStream());
                    //传递远程服务调用所需接口类，方法名，参数列表至服务提供者
                    output.writeUTF(serviceClass.getName());
                    output.writeUTF(method.getName());
                    output.writeObject(method.getParameterTypes());
                    output.writeObject(args);
                    //同步阻塞等待服务端返回应答
                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                }catch(Exception e){
                    e.printStackTrace();
                    return null;
                }finally{
                    if(output!=null){
                        try{
                            output.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(input!=null){
                        try{
                            input.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(socket!=null){
                        try{
                            socket.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}


