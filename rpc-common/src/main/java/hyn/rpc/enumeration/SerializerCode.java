package hyn.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: HYN
 * 2020/8/21 5:23 下午
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    /**
     * 不同序列化器对应的code
     */
    KRYO(0),
    JSON(1),
    HESSIAN(2);

    private final int code;

}
