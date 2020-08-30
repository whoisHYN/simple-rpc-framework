package hyn.rpc.netty.rpcdemo.api;

/**
 * @Author: HYN
 * @Description:
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
