package hyn.rpc.netty.rpcdemo.netty;

import hyn.rpc.netty.rpcdemo.client.ClientBootstrap;
import hyn.rpc.netty.rpcdemo.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: HYN
 * @Description:
 * @Modified By:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端消息并调用服务
        System.out.println("msg: " + msg);
        //客户端在调用服务器的api 时，我们需要定义一个协议
        //比如我们要求 每次发消息是都必须以某个字符串开头 "HelloService#hello#你好"
        if (msg.toString().startsWith(ClientBootstrap.PROVIDER_NAME)) {
            String str = msg.toString();
            String result = new HelloServiceImpl().hello(str.substring(str.lastIndexOf('#') + 1));
            System.out.println("NettyServerHandler中的调用结果: " + result);
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
