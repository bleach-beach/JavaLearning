package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by yanghao-012 on 2016/9/19.
 * 服务发布提供者
 */
public class RpcExporter {
    //线程池里线程执行客户端请求
    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static void exporter(String hostName,int port) throws Exception{
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(hostName,port));
        try{
            while(true){
                executor.execute(new ExporterTask(server.accept()));
            }
        }
        finally {
            server.close();
        }
    }

    private static class ExporterTask implements Runnable{

        Socket client = null;
        public ExporterTask(Socket client){
            this.client = client;
        }
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try{
                //反序列化成对象
                input = new ObjectInputStream(client.getInputStream());
                String interfaceName = input.readUTF();
                Class<?> service = Class.forName(interfaceName);
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[])input.readObject();
                Object[] arguments = (Object[])input.readObject();
                //反射调用服务实现者
                Method method = service.getMethod(methodName,parameterTypes);
                Object result = method.invoke(service.newInstance(),arguments);
                //结果对象序列化
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            }catch(Exception e){
                e.printStackTrace();
            }finally {
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
                if(client!=null){
                    try{
                        client.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
