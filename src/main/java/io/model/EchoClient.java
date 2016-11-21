package io.model;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanghao-012 on 2016/9/27.
 */
public class EchoClient {
    public Map<String,Object> buildClient(String[] args) throws IOException {
        Map<String,Object> map = new HashMap<>();

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            /*System.exit(1);*/
            map.put("code",0);
            return map;
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn = null;

        try {
             echoSocket = new Socket(hostName, portNumber);
             out =
                    new PrintWriter(echoSocket.getOutputStream(), true);
             in =
                    new BufferedReader(
                            new InputStreamReader(echoSocket.getInputStream()));

            InputStreamReader reader = new InputStreamReader(echoSocket.getInputStream());

            CharBuffer buf = CharBuffer.allocate(100);

             stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println("From Client-"+Thread.currentThread().getName()+":"+userInput);
                System.out.println("Client-"+Thread.currentThread().getName() + " : send and receive:"+in.readLine());
/*                reader.read(buf);
                buf.flip();
                System.out.println("Client-"+Thread.currentThread().getName() + " : send and receive:"+String.valueOf(buf));//反馈*/
                //手动关闭流，后续销毁线程（发送一次信息即关闭的短连接）
                echoSocket.close();
                in.close();
                out.close();
                map.put("code",0);
                map.put("stdIn",stdIn);
                return map;
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
        }finally{
            echoSocket.close();
            in.close();
            out.close();
            map.put("code",0);
            map.put("stdIn",stdIn);
            return map;
            //控制台入口不能关闭，返回上层统一关闭，因为多线程共享此通道，已关闭所有都会关闭导致线程全部销毁
/*            stdIn.close();*/
        }
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            /*System.exit(1);*/
            return;
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Client-"+Thread.currentThread().getName() + " : send and receive:"+in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            return;
        }
    }
}
