package hyn.test;

import hyn.rpc.api.ByeService;
import hyn.rpc.proxy.RpcClientProxy;

/**
 * 测试多个服务注册
 * @Author: HYN
 * 2020/8/21 11:30 上午
 */
public class TestClient2 {
    public static void main(String[] args) throws InterruptedException {
        RpcClientProxy proxy = new RpcClientProxy("localhost", 10000);
        ByeService byeService = proxy.getProxy(ByeService.class);
        while (true) {
            String res = byeService.bye("ByeServiceTest");
            System.out.println(res);
            Thread.sleep(3000);
        }
    }
}
