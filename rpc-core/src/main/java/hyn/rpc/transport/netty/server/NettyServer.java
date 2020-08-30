package hyn.rpc.transport.netty.server;

import hyn.rpc.AbstractRpcServer;
import hyn.rpc.codec.CommonDecoder;
import hyn.rpc.codec.CommonEncoder;
import hyn.rpc.provider.DefaultServiceProvider;
import hyn.rpc.registry.zk.ZkServiceRegistry;
import hyn.rpc.serializer.CommonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HYN
 */
@Slf4j
public class NettyServer extends AbstractRpcServer {

    private final CommonSerializer serializer;

    private String host;
    private int port;

    public NettyServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public NettyServer(String host, int port, int serializerCode) {
        this.host = host;
        this.port = port;
        this.serializer = CommonSerializer.getSerializerByCode(serializerCode);
        serviceProvider = new DefaultServiceProvider();
        serviceRegistry = new ZkServiceRegistry();
        scanServices();

    }

    @Override
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 256)
                    // 是否开启 TCP 底层心跳机制
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline
                                    // 30 秒之内没有收到客户端请求的话就关闭连接
                                    .addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new CommonEncoder(serializer))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
            //绑定端口，同步等待绑定成功
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            //等待服务监听端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("启动服务器发生异常", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
