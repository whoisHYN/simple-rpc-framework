package hyn.rpc.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: HYN
 * @Description:
 * @Modified By:
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道中加入处理器，先获取管道
        ChannelPipeline pipeline = ch.pipeline();
        //Netty提供的Http编解码器
        pipeline.addLast("MyHttpCodec", new HttpServerCodec());
        pipeline.addLast("MyHttpServerHandler", new MyHttpServerHandler());
    }
}
