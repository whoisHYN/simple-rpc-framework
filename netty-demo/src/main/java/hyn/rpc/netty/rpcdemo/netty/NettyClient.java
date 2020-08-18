package hyn.rpc.netty.rpcdemo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/18 3:04 下午
 * @Modified By:
 */
public class NettyClient {

    /**创建线程池*/
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().
            availableProcessors());
    private NettyClientHandler client;
    private int count = 0;

    public Object getProxy(Class<?> serviceClass, String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    System.out.println("(proxy, method, args) 进入...." + (++count) + " 次");
                    //{}  部分的代码，客户端每调用一次 hello, 就会进入到该代码
                    if (client == null) {
                        initClient();
                    }
                    client.setParam(providerName + args[0]);
                    return executor.submit(client).get();
                });
    }

    /**初始化客户端*/
    private void initClient() {
        client = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(client);
                    }
                });
        try {
            bootstrap.connect("127.0.0.1", 10086).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
