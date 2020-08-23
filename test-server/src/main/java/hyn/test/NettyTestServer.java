package hyn.test;

import hyn.rpc.RpcServer;
import hyn.rpc.api.HelloService;
import hyn.rpc.provider.DefaultServiceProvider;
import hyn.rpc.provider.ServiceProvider;
import hyn.rpc.transport.netty.server.NettyServer;
import hyn.test.impl.HelloServiceImpl;

/**
 * @Author: HYN
 */
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("localhost", 10000, 2);
        ServiceProvider provider = new DefaultServiceProvider();
        HelloService service = new HelloServiceImpl();
        provider.addServiceProvider(service);
        server.start();
    }
}
