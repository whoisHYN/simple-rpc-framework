package hyn.rpc.exception;

/**
 * @Author: HYN
 */
public class SerializationException extends RuntimeException {
    private static final long serialVersionUID = -6131992336851444916L;

    public SerializationException(String msg) {
        super(msg);
    }
}
