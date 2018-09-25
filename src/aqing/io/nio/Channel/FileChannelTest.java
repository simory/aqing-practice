package aqing.io.nio.Channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/7/2 下午11:40
 */
public class FileChannelTest {
    //文件通道总是阻塞的
    public static void main(String[] args){
        try {
            RandomAccessFile file = new RandomAccessFile("","rw");
            file.seek(1000);
            FileChannel channel = file.getChannel();
            channel.position(200);
            channel.lock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
