package hyn.rpc.socket.server.handler;

import hyn.rpc.entity.RpcRequest;
import hyn.rpc.entity.RpcResponse;
import hyn.rpc.enumeration.ResponseCode;
import hyn.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: HYN
 * 2020/8/21 10:54 上午
 */
public class RequestHandlerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            RpcRequest request = (RpcRequest)objectInputStream.readObject();
            String interfaceName = request.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            if (service == null) {
                objectOutputStream.writeObject(RpcResponse.fail(ResponseCode.CLASS_NOT_FOUND));
            } else {
                Object result = requestHandler.handle(request, service);
                objectOutputStream.writeObject(RpcResponse.success(result));
            }
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }
}
