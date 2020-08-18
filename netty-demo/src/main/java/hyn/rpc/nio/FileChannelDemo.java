package hyn.rpc.nio;

 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/4 5:29 下午
 * @Modified By:
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
//        write();
//        read();
        copy();
    }

    private static void write() throws IOException{
        String str = "Hello,黄亚宁";
        //创建输出流并获取Channel
        FileOutputStream fos = new FileOutputStream("/Users/huangyaning/test.txt");
        FileChannel channel = fos.getChannel();
        //创建ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //输入的字符串长度为15个字节
//        buffer.limit(15);

        buffer.put(str.getBytes());
        //切换读写模式
        buffer.flip();
        channel.write(buffer);
        fos.close();
    }

    private static void read() throws IOException{
        File file = new File("/Users/huangyaning/test.txt");
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        channel.read(buffer);
        byte[] array = buffer.array();
        System.out.println(new String(array));
        fis.close();
    }

    private static void copy() throws IOException {
        File file = new File("/Users/huangyaning/test.txt");
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream("/Users/huangyaning/test1.txt");
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        inChannel.read(buffer);
        buffer.flip();
        outChannel.write(buffer);
        fis.close();
        fos.close();
    }
}
