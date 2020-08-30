package hyn.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 测试API的实体类
 * @Author: HYN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloObject implements Serializable {

    private static final long serialVersionUID = -7592922462240876035L;
    private Integer id;
    private String message;
}
