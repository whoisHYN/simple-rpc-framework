package hyn.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hyn.rpc.entity.RpcRequest;
import hyn.rpc.enumeration.SerializerCode;
import hyn.rpc.exception.SerializationException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * JSON序列化器
 * @Author: HYN
 * 2020/8/21 3:37 下午
 */
@Slf4j
public class JsonSerializer implements CommonSerializer {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("序列化时发生错误：{}" + e.getMessage());
            throw new SerializationException("序列化时发生错误");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = objectMapper.readValue(bytes, clazz);
            if (obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            log.error("序列化时发生错误:", e);
            throw new SerializationException("序列化时发生错误");
        }
    }

    /**
     * 这里由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类型，需要重新判断处理
     * @param obj
     * @return
     * @throws IOException
     */
    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) obj;
        for (int i = 0; i < rpcRequest.getParameterTypes().length; i++) {
            Class<?> clazz = rpcRequest.getParameterTypes()[i];
            if (!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}
