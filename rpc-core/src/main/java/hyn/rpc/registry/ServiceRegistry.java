package hyn.rpc.registry;

/**
 * 服务注册接口
 * @Author: HYN
 * 2020/8/20 11:25 下午
 */
public interface ServiceRegistry {

    /**
     * 注册服务
     * @param service
     * @param <T>
     */
    <T> void register(T service);

    /**
     * 拉取服务
     * @param serviceName
     * @return
     */
    Object getService(String serviceName);
}
