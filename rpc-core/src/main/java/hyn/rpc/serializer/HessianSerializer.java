package hyn.rpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import hyn.rpc.enumeration.SerializerCode;
import hyn.rpc.exception.SerializationException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Author: HYN
 */
@Slf4j
public class HessianSerializer implements CommonSerializer {

    @Override
    public byte[] serialize(Object obj) {
        log.info("本次使用的是Hessian序列化");
        HessianOutput output = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            output = new HessianOutput(baos);
            output.writeObject(obj);
            return baos.toByteArray();
        } catch (IOException e) {
            log.error("Hessian 序列化时发生错误", e);
            throw new SerializationException("Hessian 序列化时发生错误");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("关闭Hessian流是发生错误");
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        HessianInput input = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            input = new HessianInput(bais);
            return input.readObject();
        } catch (IOException e) {
            log.error("Hessian 反序列化时发生错误", e);
            throw new SerializationException("Hessian 反序列化时发生错误");
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.HESSIAN.getCode();
    }
}
