package hyn.rpc.transport.handler;

import hyn.rpc.entity.RpcRequest;
import hyn.rpc.entity.RpcResponse;
import hyn.rpc.enumeration.ResponseCode;
import hyn.rpc.provider.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Socket模式下服务端执行具体任务的线程
 *
 * @Author: HYN
 */
public class RequestHandlerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceProvider serviceProvider;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceProvider serviceProvider) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void run() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            RpcRequest request = (RpcRequest)objectInputStream.readObject();
            String interfaceName = request.getInterfaceName();
            Object service = serviceProvider.getServiceProvider(interfaceName);
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
