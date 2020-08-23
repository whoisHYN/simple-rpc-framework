package hyn.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 发现服务
 *
 * @Author: HYN
 */
public interface ServiceDiscovery {

    /**
     * 发现服务
     * @param rpcServiceName
     * @return
     */
    InetSocketAddress lookUpService(String rpcServiceName);
}
