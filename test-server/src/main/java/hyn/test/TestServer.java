package hyn.test;

import hyn.rpc.api.HelloService;
import hyn.rpc.socket.server.SocketServer;

/**
 * 测试服务提供方
 * @Author: HYN
 * 2020/8/20 9:42 下午
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService service = new HelloServiceImpl();
        SocketServer server = new SocketServer();
        server.register(service, 10000);
    }
}
