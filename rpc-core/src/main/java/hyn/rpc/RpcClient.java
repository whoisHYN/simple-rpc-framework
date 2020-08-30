package hyn.rpc;

import hyn.rpc.entity.RpcRequest;
import hyn.rpc.serializer.CommonSerializer;

/**
 * 抽象出的服务消费者接口
 *
 * @Author: HYN
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.DEFAULT_SERIALIZER;

    /**
     * 消费者调用服务
     * @param rpcRequest
     * @return
     */
    Object sendRequest(RpcRequest rpcRequest);
}
