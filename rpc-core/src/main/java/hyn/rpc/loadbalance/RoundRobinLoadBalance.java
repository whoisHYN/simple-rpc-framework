package hyn.rpc.loadbalance;

import java.util.List;

/**
 * @Author: HYN
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {
    private int index = 0;
    @Override
    protected String doSelect(List<String> serviceAddresses) {
        if (index >= serviceAddresses.size()) {
            index %= serviceAddresses.size();
        }
        return serviceAddresses.get(index++);
    }
}
