package hyn.rpc;

import hyn.rpc.serializer.CommonSerializer;

/**
 * 抽象出的服务提供者接口
 * @Author: HYN
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.DEFAULT_SERIALIZER;
    /**
     * 启动服务器
     */
    void start();

    /**
     * 发布服务
     * @param service
     * @param serviceName
     * @param <T>
     */
    <T> void publishService(T service, String serviceName);
}
