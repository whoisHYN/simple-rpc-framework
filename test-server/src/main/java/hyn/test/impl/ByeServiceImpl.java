package hyn.test.impl;

import hyn.rpc.api.ByeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: HYN
 * 2020/8/21 11:31 上午
 */
public class ByeServiceImpl implements ByeService {

    private static final Logger logger = LoggerFactory.getLogger(ByeServiceImpl.class);

    @Override
    public String bye(String name) {
        logger.info("测试ByeService注册");
        return "这是ByeService返回的结果";
    }
}
