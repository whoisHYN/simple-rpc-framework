package hyn.rpc.netty.rpcdemo.api;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/17 1:05 上午
 * @Modified By:
 */
public interface HelloService {
    /**
     * 打招呼
     * @param message
     * @return
     */
    String hello(String message);
}
