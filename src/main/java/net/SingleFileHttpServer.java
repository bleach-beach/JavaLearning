package net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yanghao-012 on 2016/9/28.
 */
public class SingleFileHttpServer {



        private byte[] content;
        private byte[] header;
        private int port = 80;

        private SingleFileHttpServer(String data, String encoding,
                                       String MIMEType, int port) throws UnsupportedEncodingException {
            this(data.getBytes(encoding), encoding, MIMEType, port);
        }

        public SingleFileHttpServer(byte[] data, String encoding, String MIMEType, int port) throws UnsupportedEncodingException {
            this.content = data;
            this.port = port;
            String header = "HTTP/1.0 200 OK\r\n" +
                    "Server: OneFile 1.0\r\n" +
                    "Content-length: " + this.content.length + "\r\n" +
                    "Content-type: " + MIMEType + "\r\n\r\n";
            this.header = header.getBytes("ASCII");
        }

        public void run() {
            //        System.out.println(Thread.currentThread().getName());
            try {
                ServerSocket server = new ServerSocket(this.port);
                System.out.println("Accepting connections on port " + server.getLocalPort());
                System.out.println("Data to be sent:");
                System.out.println();
                ExecutorService executorService = Executors.newFixedThreadPool(6);
                while (true) {
                    System.out.println(">>>>>等待客户端连接>>>>>");
                    Socket connection = server.accept();

                    executorService.execute(new Task(connection, content, header));
                }
            } catch (IOException e) {
                System.err.println("Could not start server. Port Occupied");
            }
        }

        public static void main(String args[]) {
            try {
                String contentType = "text/plain";
                if (args[0].endsWith(".html") || args[0].endsWith(".htm")) {
                    contentType = "text/html";
                }

                InputStream in = new FileInputStream(args[0]);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                byte[] data = out.toByteArray();

                //设置监听端口
                int port;
                try {
                    port = Integer.parseInt(args[1]);
                    if (port < 1 || port > 65535) {
                        port = 8081;
                    }
                } catch (Exception e) {
                    port = 8081;
                }

                String encoding = "ASCII";
                if (args.length > 2) {
                    encoding = args[2];
                }

                SingleFileHttpServer t = new SingleFileHttpServer(data, encoding, contentType, port);
                /**
                 * 在main线程启动http-server
                 */
                t.run();

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Usage:java SingleFileHTTPServer filename port encoding");
            } catch (Exception e) {
                System.err.println(e);// TODO: handle exception
            }
        }
    private class Task implements Runnable {

        private Socket connection;
        private byte[] content;
        private byte[] header;

        public Task(Socket connection, byte[] content, byte[] header) {
            this.connection = connection;
            this.content = content;
            this.header = header;
        }

        @Override
        public void run() {
            try {

                System.out.println(Thread.currentThread().getName() + "任务开始执行");
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                InputStream in = new BufferedInputStream(connection.getInputStream());

                StringBuffer request = new StringBuffer();
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) {
                        break;
                    }
                    request.append((char) c);
                }

                //如果检测到是HTTP/1.0及以后的协议，按照规范，需要发送一个MIME首部
                if (request.toString().indexOf("HTTP/") != -1) {
                    out.write(this.header);
                }
                out.write(this.content);
                out.flush();
                System.out.println(Thread.currentThread().getName() + "任务执行结束");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

