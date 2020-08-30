package hyn.rpc.loadbalance;

import java.util.List;

/**
 * 负载均衡抽象类
 *
 * @Author: HYN
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses) {
        if (serviceAddresses == null || serviceAddresses.isEmpty()) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses);
    }

    /**
     * 在子类中实现具体的负载均衡策略
     * @param serviceAddresses
     * @return
     */
    protected abstract String doSelect(List<String> serviceAddresses);
}
