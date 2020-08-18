package hyn.rpc.netty.rpcdemo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/18 2:28 下午
 * @Modified By:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {

    /**上下文*/
    private ChannelHandlerContext context;
    /**需要返回的结果*/
    private String result;
    /**客户端调用方法时传入的参数*/
    private String param;

    /**被代理对象调用, 发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果 (3)->(5) */
    @Override
    public synchronized String call() throws Exception {
        System.out.println(" call1 被调用 ");
        context.writeAndFlush(param);
        //等待channelRead方法获取服务端结果后被唤醒
        wait();
        System.out.println(" call2 被调用");
        return result;
    }

    /**与服务器的连接创建后，就会被调用, 这个方法是第一个被调用(1)*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" channelActive 被调用...");
        //因为我们在其它方法会使用到 ctx
        this.context = ctx;
    }

    /**收到服务器的数据后，调用方法 (4)*/
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" channelRead 被调用...");
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /** (2)*/
    void setParam(String param) {
        System.out.println(" setParam ");
        this.param = param;
    }
}
