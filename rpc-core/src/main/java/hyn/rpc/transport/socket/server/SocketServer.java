package hyn.rpc.transport.socket.server;

import hyn.rpc.provider.ServiceProvider;
import hyn.rpc.transport.handler.RequestHandler;
import hyn.rpc.transport.handler.RequestHandlerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 远程方法调用的提供者（服务端）
 *
 * @Author: HYN
 */
public class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private final ExecutorService threadPool;
    private static final int CORE_POOL_SIZE = 5;
    public static final int MAXIMUM_POOL_SIZE = 50;
    public static final int KEEP_ALIVE_TIME = 60;
    public static final int BLOCKING_QUEUE_CAPACITY = 100;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceProvider serviceProvider;

    public SocketServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                workingQueue, threadFactory);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费方连接：{} : {}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceProvider));
            }
            threadPool.shutdownNow();
        } catch (IOException e) {
            logger.error("服务器启动时发生错误", e);
        }
    }
}
