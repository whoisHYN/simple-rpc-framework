package hyn.rpc.exception;

/**
 * @Author: HYN
 * 2020/8/21 5:28 下午
 */
public class SerializationException extends RuntimeException {
    private static final long serialVersionUID = -6131992336851444916L;

    public SerializationException(String msg) {
        super(msg);
    }
}
