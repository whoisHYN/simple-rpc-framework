package hyn.test;

import hyn.rpc.RpcClient;
import hyn.rpc.api.HelloObject;
import hyn.rpc.api.HelloService;
import hyn.rpc.proxy.RpcClientProxy;
import hyn.rpc.transport.netty.client.NettyClient;

/**
 * @Author: HYN
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("localhost", 10000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService service = proxy.getProxy(HelloService.class);
//        HelloObject object = new HelloObject(666, "你好啊Netty服务器");
        HelloObject object = service.transportObject();
        System.out.println(object.getId() + ": " + object.getMessage());
    }
}
