package hyn.rpc.netty.rpcdemo.client;

import hyn.rpc.netty.rpcdemo.api.HelloService;
import hyn.rpc.netty.rpcdemo.netty.NettyClient;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/17 8:52 下午
 * @Modified By:
 */
public class ClientBootstrap {

    /**定义协议头*/
    public static final String PROVIDER_NAME = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        //创建一个消费者
        NettyClient client = new NettyClient();
        HelloService service = (HelloService)client.getProxy(HelloService.class, PROVIDER_NAME);
        //通过代理对象调用服务提供者的方法
        while (true) {
            Thread.sleep(2 * 1000);
            String res = service.hello("你好啊");
            System.out.println("调用结果 res = " + res);
        }
    }
}
