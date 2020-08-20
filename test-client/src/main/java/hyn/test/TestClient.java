package hyn.test;

import hyn.rpc.api.HelloObject;
import hyn.rpc.api.HelloService;
import hyn.rpc.proxy.RpcClientProxy;

/**
 * 测试服务消费方
 *
 * @Author: HYN
 * 2020/8/20 9:29 下午
 */
public class TestClient {
    public static void main(String[] args) throws InterruptedException {
        RpcClientProxy proxy = new RpcClientProxy("localhost", 10000);
        HelloService service = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "我是测试的服务消费方");
        while (true) {
            Thread.sleep(2000);
            String hello = service.hello(object);
            System.out.println(hello);
        }
    }
}
