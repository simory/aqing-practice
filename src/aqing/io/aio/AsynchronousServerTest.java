package aqing.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Description:异步io
 * @Author: fuqi
 * @Date: 18/10/13 下午3:08
 */
public class AsynchronousServerTest {

    public void test() throws IOException {
        final AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8080));
        serverSocket.accept(serverSocket, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
            //为异步操作指定CompletionHandler回调函数
            @Override
            public void completed(AsynchronousSocketChannel result, AsynchronousServerSocketChannel attachment) {
                serverSocket.accept(serverSocket,this);
            }

            @Override
            public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {

            }
        });
    }
}
