package hyn.test;

import hyn.rpc.api.HelloObject;
import hyn.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: HYN
 * 2020/8/20 9:43 下午
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "这是服务调用的返回值，id=" + object.getId();
    }
}
