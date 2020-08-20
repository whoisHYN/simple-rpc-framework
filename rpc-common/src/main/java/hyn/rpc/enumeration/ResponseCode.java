package hyn.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: HYN
 * 2020/8/19 9:49 下午
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    /**
     * 调用成功
     */
    SUCCESS(200, "方法调用成功"),

    /**
     * 方法调用失败
     */
    FAIL(500, "方法调用失败"),

    /**
     * 未找到指定方法
     */
    METHOD_NOT_FOUND(500, "未找到指定方法"),

    /**
     * 未找到指定类
     */
    CLASS_NOT_FOUND(500, "未找到指定类");

    private final int code;
    private final String message;
}
