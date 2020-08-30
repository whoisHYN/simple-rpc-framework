package hyn.test;

import hyn.rpc.RpcClient;
import hyn.rpc.api.ByeService;
import hyn.rpc.proxy.RpcClientProxy;
import hyn.rpc.transport.socket.client.SocketClient;

/**
 * 测试多个服务注册
 * @Author: HYN
 */
public class TestClient2 {
    public static void main(String[] args) throws InterruptedException {
        RpcClient client = new SocketClient("localhost", 10000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        ByeService byeService = proxy.getProxy(ByeService.class);
        while (true) {
            String res = byeService.bye("ByeServiceTest");
            System.out.println(res);
            Thread.sleep(3000);
        }
    }
}
