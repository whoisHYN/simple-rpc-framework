package hyn.rpc.entity;

import hyn.rpc.enumeration.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 服务提供方在执行成功或失败后需要向消费方返回的信息
 *
 * @Author: HYN
 * 2020/8/19 9:29 下午
 */
@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    private static final long serialVersionUID = -6029041424281231500L;

    /**
     * 响应对应的请求号
     */
    private String requestId;
    /**
     * 响应码
     */
    private Integer statusCode;
    /**
     * 响应状态补充信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setData(data);
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        return response;
    }
    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = success(data);
        response.setRequestId(requestId);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = fail(code);
        response.setRequestId(requestId);
        return response;
    }
}
