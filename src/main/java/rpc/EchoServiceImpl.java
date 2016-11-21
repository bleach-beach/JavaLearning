package rpc;

/**
 * Created by yanghao-012 on 2016/9/19.
 */
public class EchoServiceImpl implements  EchoService {
    public String echo(String ping) {
        return ping!=null?ping+"--->I am ok.":"I am ok.";
    }
}
