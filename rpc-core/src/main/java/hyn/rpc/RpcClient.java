package hyn.rpc;

import hyn.rpc.entity.RpcRequest;

/**
 * 抽象出的服务消费者接口
 *
 * @Author: HYN
 * 2020/8/21 12:43 下午
 */
public interface RpcClient {

    /**
     * 消费者调用服务
     * @param rpcRequest
     * @return
     */
    Object sendRequest(RpcRequest rpcRequest);
}
