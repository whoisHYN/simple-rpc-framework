package hyn.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: HYN
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    /**
     * 根据code判断是请求包还是回复包
     */
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
