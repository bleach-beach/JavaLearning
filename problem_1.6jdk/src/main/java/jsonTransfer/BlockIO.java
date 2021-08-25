package main.java.jsonTransfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class BlockIO {
    public static int DEFAULT_PORT = 7;

    public static void main(String[] args){

        int port;

        try{
            port = Integer.parseInt(args[0]);
        }catch(RuntimeException ex){
            port = DEFAULT_PORT;
        }

        try{
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ObjectOutputStream oout = new ObjectOutputStream(clientSocket.getOutputStream());
                String inputLine;
            while((inputLine = in.readLine())!=null){
                if("p1".equals(inputLine)){
                    Problem1 problem1 = new Problem1();
                    ResultBean result = problem1.doService();
                    oout.writeObject(result);
                    System.out.println("receive then send: "+result);
                }else{
                    oout.writeObject(inputLine);
                    System.out.println("receive then send: "+inputLine);
/*                    out.println(inputLine);
                    System.out.println("receive then send: "+inputLine);*/
                }

            }
        } catch(IOException e){
            System.out.println("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }
}
