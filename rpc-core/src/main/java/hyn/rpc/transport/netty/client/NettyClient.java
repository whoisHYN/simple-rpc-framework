package hyn.rpc.transport.netty.client;

import hyn.rpc.RpcClient;
import hyn.rpc.codec.CommonDecoder;
import hyn.rpc.codec.CommonEncoder;
import hyn.rpc.entity.RpcRequest;
import hyn.rpc.entity.RpcResponse;
import hyn.rpc.serializer.CommonSerializer;
import hyn.rpc.serializer.JsonSerializer;
import hyn.rpc.serializer.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: HYN
 */
@Slf4j
public class NettyClient implements RpcClient {

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new CommonDecoder())
                                .addLast(new NettyClientHandler<>());
                    }
                });

    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("客户端已连接 {} : {}", host, port);
            Channel channel = channelFuture.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future -> {
                    if (future.isSuccess()) {
                        log.info(String.format("客户端发送消息：%s", rpcRequest.toString()));
                    } else {
                        log.error("发送消息是发生错误", future.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
                RpcResponse response = channel.attr(key).get();
                return response.getData();
            }
        } catch (InterruptedException e) {
            log.error("发送消息时发生错误", e);
        }
        return null;
    }
}
