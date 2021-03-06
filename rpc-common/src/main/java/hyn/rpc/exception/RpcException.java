package hyn.rpc.exception;

import hyn.rpc.enumeration.RpcError;

/**
 * @Author: HYN
 */
public class RpcException extends RuntimeException {

    private static final long serialVersionUID = 6089508027761182873L;

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

    public RpcException(String detail, Throwable cause) {
        super(detail, cause);
    }
}
