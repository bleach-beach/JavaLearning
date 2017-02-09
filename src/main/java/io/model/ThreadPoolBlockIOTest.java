package io.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yanghao-012 on 2016/9/28.
 */
public class ThreadPoolBlockIOTest {
    public static void  main(String args[]){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadPoolBlockIO.buildServer(8085);
            }
        }).start();

        //用线程异步构建客户端
        final String[] clientArgs = new String[]{"127.0.0.1","8085"};
        final List<BufferedReader> listStd = new ArrayList<>();
        final int clientNumber = 5;
        try {
            for(int i=0;i<clientNumber;i++){
                //创建clientNumber个客户端线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Build ClientThread "+Thread.currentThread().getName());
                            EchoClient client  = new EchoClient();
                            Map<String,Object> map;
                            map = client.buildClient(clientArgs);
                            if(Integer.parseInt(map.get("code").toString())==0){
                                System.out.println("Client-"+Thread.currentThread().getName()+"线程结束");
                                if(map.get("stdIn")!=null){
                                    listStd.add((BufferedReader) map.get("stdIn"));
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("出错2");
                        }
                    }
                }).start();
                System.out.println("client build number "+i);

                //该线程统一释放客户端控制台连接资源
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            if(listStd.size()==clientNumber){
                                for(BufferedReader bf:listStd){
                                    try {
                                        bf.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }finally{
                                        continue;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出错1");
        }
    }
}
