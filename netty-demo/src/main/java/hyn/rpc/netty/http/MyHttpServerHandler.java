package hyn.rpc.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/6 12:05 下午
 * @Modified By:
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**此方法读取客户端数据*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断是不是客户端请求
        if (msg instanceof HttpRequest) {
            System.out.println("msg类型是" + msg.getClass());
            System.out.println("客户端地址是" + ctx.channel().remoteAddress());

            //进制请求图标，也就是favicon.ico，先获取uri
            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了favicon，不作响应");
                return;
            }
            //以http协议回复信息给客户端
            ByteBuf content = Unpooled.copiedBuffer("Hello, 我是服务器...", CharsetUtil.UTF_8);

            //构造一个HttpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //将构建好的Response返回
            ctx.writeAndFlush(response);
        }
    }
}
