package hyn.rpc.api;

/**
 * @Author: HYN
 * 2020/8/20 5:14 下午
 */
public interface HelloService {

    /**
     * 发送hello信息
     * @param object
     * @return
     */
    String hello(HelloObject object);

}
