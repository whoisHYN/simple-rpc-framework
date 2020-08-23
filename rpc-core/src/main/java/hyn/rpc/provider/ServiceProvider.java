package hyn.rpc.provider;

/**
 * 服务添加和获取，这是最初的模式，基于硬编码的方式注册服务，体验不好，后期改为基于zookeeper实现服务注册与发现
 * @Author: HYN
 * 2020/8/20 11:25 下午
 */
public interface ServiceProvider {

    /**
     * 注册服务
     * @param service
     * @param <T>
     */
    <T> void addServiceProvider(T service);

    /**
     * 拉取服务
     * @param serviceName
     * @return
     */
    Object getServiceProvider(String serviceName);
}
