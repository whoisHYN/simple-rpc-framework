package hyn.rpc.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @Author: HYN
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
