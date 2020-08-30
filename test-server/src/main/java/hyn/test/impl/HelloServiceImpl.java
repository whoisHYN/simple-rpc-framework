package hyn.test.impl;

import hyn.rpc.api.HelloObject;
import hyn.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: HYN
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "这是服务调用的返回值，id=" + object.getId();
    }

    @Override
    public HelloObject transportObject() {
        logger.info("接收到复杂对象测试请求");
        HelloObject object = new HelloObject();
        object.setId(666);
        object.setMessage("测试传输复杂对象成功");
        return object;
    }

}
