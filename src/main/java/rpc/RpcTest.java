package rpc;

import java.net.InetSocketAddress;

/**
 * Created by yanghao-012 on 2016/9/19.
 */
public class RpcTest {
    public static void main(String args[]){
        //启动线程异步创建服务提供者服务器
        new Thread(new Runnable() {
            public void run() {
                try{
                    RpcExporter.exporter("localhost",8088);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        RpcImporter<EchoService> importer = new RpcImporter<EchoService>();
        EchoService echo = importer.importer(EchoServiceImpl.class,new InetSocketAddress("localhost",8088));
        System.out.println(echo.echo("Are you ok?"));
    }
}
