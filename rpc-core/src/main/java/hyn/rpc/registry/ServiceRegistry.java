package hyn.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册
 *
 * @Author: HYN
 */
public interface ServiceRegistry {

    /**
     * 注册服务
     * @param rpcServiceName
     * @param inetSocketAddress
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
