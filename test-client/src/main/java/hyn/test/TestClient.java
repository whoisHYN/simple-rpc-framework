package hyn.test;

import hyn.rpc.api.HelloObject;
import hyn.rpc.api.HelloService;
import hyn.rpc.proxy.RpcClientProxy;
import hyn.rpc.transport.socket.client.SocketClient;

/**
 * 测试服务消费方
 *
 * @Author: HYN
 */
public class TestClient {
    public static void main(String[] args) throws InterruptedException {
        SocketClient socketClient = new SocketClient("localhost", 10000);
        RpcClientProxy proxy = new RpcClientProxy(socketClient);
        HelloService service = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "我是测试的服务消费方");
        while (true) {
//            String hello = service.hello(object);
            HelloObject res = service.transportObject();
            System.out.println(res.getClass().getCanonicalName());
            System.out.println(res.getId() + " " + res.getMessage());
            Thread.sleep(5000);
        }
    }
}
