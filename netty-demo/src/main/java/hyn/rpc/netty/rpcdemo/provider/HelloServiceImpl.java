package hyn.rpc.netty.rpcdemo.provider;

import hyn.rpc.netty.rpcdemo.api.HelloService;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/17 1:07 上午
 * @Modified By:
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String message) {
        System.out.println("收到客户端的消息=" + message);
        if (message != null && message.trim().length() != 0) {
            return "你好客户端，我已经收到你的消息 [" + message + "].";
        } else {
            return "你好客户端，我已经收到你的消息";
        }
    }
}
