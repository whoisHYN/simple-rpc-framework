package hyn.test;

import hyn.rpc.RpcServer;
import hyn.rpc.api.HelloService;
import hyn.rpc.registry.DefaultServiceRegistry;
import hyn.rpc.registry.ServiceRegistry;
import hyn.rpc.transport.netty.server.NettyServer;
import hyn.test.impl.HelloServiceImpl;

/**
 * @Author: HYN
 */
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer();
        ServiceRegistry registry = new DefaultServiceRegistry();
        HelloService service = new HelloServiceImpl();
        registry.register(service);
        server.start(10000);
    }
}
