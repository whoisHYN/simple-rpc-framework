package hyn.rpc.codec;

import hyn.rpc.entity.RpcRequest;
import hyn.rpc.entity.RpcResponse;
import hyn.rpc.enumeration.PackageType;
import hyn.rpc.enumeration.RpcError;
import hyn.rpc.exception.RpcException;
import hyn.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 通用的解码拦截器
 * @Author: HYN
 * 2020/8/21 8:52 下午
 */
@Slf4j
public class CommonDecoder extends ReplayingDecoder {

    public static final int MAGIC_NUMBER = 0xCAFEBABE;

    /**
     * +---------------+---------------+-----------------+-------------+
     * |  Magic Number |  Package Type | Serializer Type | Data Length |
     * |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
     * +---------------+---------------+-----------------+-------------+
     * |                          Data Bytes                           |
     * |                   Length: ${Data Length}                      |
     * +---------------------------------------------------------------+
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //读取魔术
        int magic = in.readInt();
        if (magic != MAGIC_NUMBER) {
            log.error("无法识别该协议包: {}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        //读取数据类型，是 RpcRequest 还是 RPCResponse
        int packageCode = in.readInt();
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            log.error("无法识别该数据包: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        //读取序列化器类型
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getSerializerByCode(serializerCode);
        if (serializer == null) {
            log.error("找不到对应的序列化器: {}", serializerCode);
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //读取数据长度
        int length = in.readInt();
        byte[] bytes = new byte[length];
        //读取数据
        in.readBytes(bytes);
        Object object = serializer.deserialize(bytes, packageClass);
        out.add(object);
    }
}
