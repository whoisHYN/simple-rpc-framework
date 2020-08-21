package hyn.test;

import hyn.rpc.api.ByeService;
import hyn.rpc.api.HelloService;
import hyn.rpc.registry.DefaultServiceRegistry;
import hyn.rpc.registry.ServiceRegistry;
import hyn.rpc.socket.server.SocketServer;
import hyn.test.impl.ByeServiceImpl;
import hyn.test.impl.HelloServiceImpl;

/**
 * 测试服务提供方
 * @Author: HYN
 * 2020/8/20 9:42 下午
 */
public class TestServer {
    public static void main(String[] args) {
        //测试注册多个服务
        HelloService helloService = new HelloServiceImpl();
        ByeService byeService = new ByeServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        serviceRegistry.register(byeService);
        SocketServer server = new SocketServer(serviceRegistry);
        server.start(10000);
    }
}
