package hyn.rpc.api;

/**
 * @Author: HYN
 */
public interface HelloService {

    /**
     * 发送hello信息
     * @param object
     * @return
     */
    String hello(HelloObject object);

    /**
     * 测试发送与接收复杂对象
     * @return
     */
    HelloObject transportObject();

}
