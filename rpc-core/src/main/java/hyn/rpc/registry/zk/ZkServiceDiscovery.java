package hyn.rpc.registry.zk;

import hyn.rpc.enumeration.RpcError;
import hyn.rpc.exception.RpcException;
import hyn.rpc.loadbalance.LoadBalance;
import hyn.rpc.loadbalance.RandomLoadBalance;
import hyn.rpc.registry.ServiceDiscovery;
import hyn.rpc.registry.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author: HYN
 */
@Slf4j
public class ZkServiceDiscovery implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    public ZkServiceDiscovery() {
        this.loadBalance = new RandomLoadBalance();
    }

    public ZkServiceDiscovery(LoadBalance loadBalance) {
        this.loadBalance = loadBalance;
    }

    @Override
    public InetSocketAddress lookUpService(String rpcServiceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList.size() == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND, rpcServiceName);
        }
        //使用负载均衡算法选择对应的服务,形如 127.0.0.1:2181
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList);
        log.info("成功找到对应的服务: [{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
