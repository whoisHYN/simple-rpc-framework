package hyn.rpc.serializer;


/**
 * 序列化器接口
 * @Author: HYN
 */
public interface CommonSerializer {

    int KRYO_SERIALIZER = 0;
    int JSON_SERIALIZER = 1;
    int HESSIAN_SERIALIZER = 2;
    int PROTOBUF_SERIALIZER = 3;
    int DEFAULT_SERIALIZER = KRYO_SERIALIZER;

    /**
     * 根据code获取对应的序列化器
     * @param code
     * @return
     */
    static CommonSerializer getSerializerByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            default:
                return null;
        }
    }

    /**
     * 序列化
     * @param obj
     * @return
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     * @param bytes
     * @param clazz
     * @return
     */
    Object deserialize(byte[] bytes, Class<?> clazz);

    /**
     * 序列化处理器编号
     * @return
     */
    int getCode();
}
