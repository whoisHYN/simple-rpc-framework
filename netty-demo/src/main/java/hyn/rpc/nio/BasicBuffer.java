package hyn.rpc.nio;

import java.nio.IntBuffer;

/**
 * @Author: HYN
 * @Description:
 * @Date: 2020/8/4 2:51 下午
 * @Modified By:
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 3);
        }
        //从buffer读取数据，需要先进行读写模式的切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
