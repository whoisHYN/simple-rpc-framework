package hyn.rpc.transport.netty.server;

import hyn.rpc.entity.RpcRequest;
import hyn.rpc.entity.RpcResponse;
import hyn.rpc.registry.DefaultServiceRegistry;
import hyn.rpc.registry.ServiceRegistry;
import hyn.rpc.transport.handler.RequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty服务端处理器
 * @Author: HYN
 * 2020/8/21 9:35 下午
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    public NettyServerHandler() {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            log.info("服务器接收到请求: {}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(msg, service);
            ChannelFuture channelFuture = ctx.writeAndFlush(RpcResponse.success(result));
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理过程调用时发生错误");
        cause.printStackTrace();
        ctx.close();
    }
}
