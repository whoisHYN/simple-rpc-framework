package hyn.rpc.transport.socket.client;

import hyn.rpc.RpcClient;
import hyn.rpc.entity.RpcRequest;
import hyn.rpc.proxy.RpcClientProxy;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SocketClient implements RpcClient{

    private String host;
    private int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用时发生错误", e);
            return null;
        }
    }
}
