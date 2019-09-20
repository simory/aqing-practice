package aqing.io.nio.Channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/7/3 上午7:33
 */
public class SocketChannelTest {

    //观察者模式--监听事件
    public void seletor() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//3.实现非阻塞(cpu非空闲等待)的关键
        Selector selector = Selector.open();//1.调度员，实现多路复用的基础
        ServerSocketChannel ssc = ServerSocketChannel.open();//2.操作系统底层的抽象，直接访问内核空间的transferTo/transferFrom
        ssc.configureBlocking(false);//非阻塞模式--因为阻塞模式下，注册操作是不允许的，会抛出IllegalBlockingModeException
        ssc.socket().bind(new InetSocketAddress(8080));
        ssc.register(selector,SelectionKey.OP_ACCEPT);//注册监听事件
        while(true){//**单线程轮训事件机制--高效定位就绪channel，来决定做什么，仅仅select阶段阻塞，避免大量客户端连接时，频繁线程切换带来的问题。应用的扩张能力有了非常大的提高
            Set selectedKeys = selector.selectedKeys();//取得所有key集合
            Iterator it = selectedKeys.iterator();
            while(it.hasNext()){
                /**缺点是多路复用的下面的过程是同步非阻塞的。
                   这会带来什么问题呢？
                 *1.每个channel都是耗时操作，由于这个线程是同步的，当耗时较长容易积压过多的channel任务
                 * 怎么解决？
                 * 利用观察者模式，将监听客户端请求和服务端读取客户端请求和处理逻辑分离
                 * 具体来说就是将后者(服务客户端请求和做业务逻辑处理)异步到另外的线程处理--不过这是不需要返回结果的情况
                 * 当需要返回结果可以利用线程的Future等回调来获得请求结果
                 * 为了效率可以使用线程池
                 * */
                SelectionKey key = (SelectionKey)it.next();
                if((key.readyOps() & SelectionKey.OP_ACCEPT) ==SelectionKey.OP_ACCEPT){
                    ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = ssChannel.accept();//接收到服务器端的请求
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    it.remove();
                }else if((key.readyOps() & SelectionKey.OP_READ) ==SelectionKey.OP_READ){
                    SocketChannel sChannel = (SocketChannel)key.channel();
                    while(true){
                        byteBuffer.clear();
                        int n = sChannel.read(byteBuffer);//读取数据
                        if(n <=0) break;
                        byteBuffer.flip();
                    }
                    it.remove();
                }
                }
        }
    }
    public static void main(String[] args){

    }
}
