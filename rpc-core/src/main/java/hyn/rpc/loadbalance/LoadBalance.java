package hyn.rpc.loadbalance;

import java.util.List;

/**
 * 负载均衡接口
 *
 * @Author: HYN
 */
public interface LoadBalance {

    /**
     * 按照特定的负载均衡策略，从服务提供者列表中选择一个
     * @param serviceAddresses
     * @return
     */
    String selectServiceAddress(List<String> serviceAddresses);
}
