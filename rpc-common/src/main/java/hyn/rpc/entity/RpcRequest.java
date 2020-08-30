package hyn.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装消费者向提供者发送的消息
 *
 * @Author: HYN
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -6749297504951883136L;

    /**
     * 请求号
     */
    private String requestId;
    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法参数
     */
    private Object[] parameters;
    /**
     * 方法参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 是否是心跳包
     */
    private boolean heartBeat;
}
