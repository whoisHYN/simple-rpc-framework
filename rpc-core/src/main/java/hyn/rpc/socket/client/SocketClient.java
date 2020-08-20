package hyn.rpc.socket.client;

import hyn.rpc.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 远程方法调用的消费者（客户端）
 *
 * @Author: HYN
 * 2020/8/20 1:26 下午
 */
public class SocketClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    public Object sendRequest(RpcRequest request, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时发生错误", e);
            return null;
        }
    }
}
