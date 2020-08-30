package hyn.rpc.transport.handler;

import hyn.rpc.entity.RpcRequest;
import hyn.rpc.entity.RpcResponse;
import hyn.rpc.enumeration.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实际进行过程调用的处理器
 *
 * @Author: HYN
 */
@Slf4j
public class RequestHandler {

    public Object handle(RpcRequest request, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(request, service);
            log.info("服务:{} 成功调用方法:{}", request.getInterfaceName(), request.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("调用或发送时有错误发生：", e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest request, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, request.getParameters());
    }
}