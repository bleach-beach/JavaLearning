package io.model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by yanghao-012 on 2016/10/20.
 */
public class NonBlockingIO {
    public static void buildServer(int port){

        ServerSocketChannel serverSocketChannel;
        Selector selector;
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();
        try {
            serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(port);
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(true){
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try{
                    if(key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from" + client);
                        client.configureBlocking(false);
/*                        SelectionKey clientKey = client.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ);*/
                        SelectionKey clientKey = client.register(selector,SelectionKey.OP_READ);
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        clientKey.attach(buffer);
                    }
                    //客户端发送，接收后就会断开连接
                    //SO服务器端读取并反馈后也会断开连接：key.cancel or client.close;
                    //如果关闭了与SelectionKey对象关联的Channel对象，或者调用了SelectionKey对象的cancel方法，这个SelectionKey对象就会被加入到cancelled-keys集合中，表示这个SelectionKey对象已经被取消。
                    if(key.isValid()){
                        if(key.isReadable()){
                            SocketChannel client = (SocketChannel)key.channel();
                            ByteBuffer output = (ByteBuffer) key.attachment();
                            int count =  client.read(output);
                            CharBuffer charBuffer = null;
                            if (count > 0)
                            {
                                //把limit设为position，把position设为0，这样读取buffer时，刚好读取其已经读取多少内容的buffer
                                output.flip();

                                charBuffer = decoder.decode(output);
                                String message = charBuffer.toString();

                                System.out.println(message);
                                key.interestOps(SelectionKey.OP_WRITE);
                            }else{
                                key.cancel();
                            }
                        }
                        if(key.isWritable()){
                            SocketChannel client = (SocketChannel)key.channel();
                            ByteBuffer output = (ByteBuffer) key.attachment();
                            output.flip();
                            client.write(output);

                            output.compact();
                            output.clear();
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    }

                }catch(Exception iex){
                    key.channel();
                    try{
                        key.channel().close();
                    }catch (IOException iex2){

                    }
                }

            }
        }


    }

    public static String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            //用这个的话，只能输出来一次结果，第二次显示为空
// charBuffer = decoder.decode(buffer);
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}
