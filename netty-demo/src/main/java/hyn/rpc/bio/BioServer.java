package hyn.rpc.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: HYN
 * @Description: 演示服务端使用BIO的情况下处理客户端请求的过程，客户端使用telnet进行模拟即可
 * @Date: 2020/8/4 12:01 下午
 * @Modified By:
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        ExecutorService threadPoll = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器已启动...");
        while (true) {
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字= " + Thread.currentThread().getName());
            System.out.println("等待连接");
            Socket socket= serverSocket.accept();
            System.out.println("连接到一个客户端");
            threadPoll.execute(() -> handle(socket));
        }
    }

    private static void handle(Socket socket) {
        try {
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());

            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());

                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭连接...");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
