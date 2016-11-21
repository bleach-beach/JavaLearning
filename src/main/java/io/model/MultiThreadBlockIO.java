package io.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yanghao-012 on 2016/9/27.
 * 存在问题：每次接收到新的连接都要新建一个线程，处理完成后销毁线程，代价大。当有大量地短连接出现时，性能比较低。
 */
public class MultiThreadBlockIO {
    public static void buildServer(int port){
        Socket clientSocket = null;

        int ThreadNumber = 0;
        try(
                ServerSocket serverSocket = new ServerSocket(port);
                ){
            while (true) {
                //监听到客户端则阻塞,执行代码块
                clientSocket = serverSocket.accept();

                // MultiThread
                new Thread(new ServerHandler(clientSocket)).start();

                ThreadNumber++;
                System.out.println("ServerThreadNumber "+ThreadNumber);
            }
        }catch(IOException e){
            System.out.println(
                    "Exception caught when trying to listen on port " + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }

    private static class ServerHandler implements Runnable{

        private Socket clientSocket;

        public ServerHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            System.out.println("Build ServerThread "+Thread.currentThread().getName());
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while((inputLine = in.readLine())!=null){
                    out.println("From Server-"+Thread.currentThread().getName()+":"+inputLine);
                    System.out.println("Server-"+Thread.currentThread().getName()+"  receive then send:"+inputLine);
                    //手动关闭流，或者客户端关闭通道，则线程才会自动销毁，否则一直循环，等于长连接
/*                    out.close();
                    in.close();*/
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }finally {
                out.close();
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //销毁线程
                System.out.println("Server-"+Thread.currentThread().getName()+"线程销毁");
            }
        }
    }
}
