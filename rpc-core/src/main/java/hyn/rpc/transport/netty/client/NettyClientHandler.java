package hyn.rpc.transport.netty.client;

import hyn.rpc.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty客户端处理器
 * @Author: HYN
 * 2020/8/21 9:35 下午
 */
@Slf4j
public class NettyClientHandler<T> extends SimpleChannelInboundHandler<RpcResponse<T>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse<T> msg) throws Exception {
        try {
            log.info(String.format("客户端接收到消息: %s", msg));
            AttributeKey<RpcResponse<T>> key = AttributeKey.valueOf("RpcResponse");
            ctx.channel().attr(key).set(msg);
            ctx.channel().close();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("过程调用中发生错误");
        cause.printStackTrace();
        ctx.close();
    }
}
