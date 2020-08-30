package hyn.rpc.netty.rpcdemo.provider;

import hyn.rpc.netty.rpcdemo.netty.NettyServer;

/**
 * @Author: HYN
 * @Description:
 * @Modified By:
 */
public class ServerBootstrapDemo {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 10086);
    }
}
