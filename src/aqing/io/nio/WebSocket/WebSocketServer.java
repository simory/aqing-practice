package aqing.io.nio.WebSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description:websocket的server端.服务启动类
 * @Author: fuqi
 * @Date: 2019/5/8 上午7:18
 */
public class WebSocketServer {

    /**
     * 1.WebSocket是HTML5开始提供的一种浏览器与服务器间基于TCP进行全双工通信的网络技术
     * 同一时刻既可以发送消息，也可以接收消息
     * 相比HTTP的半双工协议，性能提升更大
     * 本质是TCP协议，因此数据传输的稳定性和数据传输量方面和轮训以及技术相比，具有很大性能优势
     * 2.WebSocket通信协议于2011年被IETF定为标准RFC6455，WebSocket API被W3C定为标准
     * **/
    public void run(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("http-codec",new HttpServerCodec());//将请求和应答消息编码或者解码为HTTP消息
                    pipeline.addLast("aggregator",new HttpObjectAggregator(65536));//将HTTP消息的多个部分组合成一条完整的HTTP消息
                    socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());//向客户端大宋HTML5文件，主要用于支持服务端和浏览器进行WebSocket通信
                    pipeline.addLast("handler",new WebSocketServerProtocolHandler(""));//Websocket服务端handler
                }

            });
            Channel ch = b.bind(port).sync().channel();
            ch.closeFuture().sync();
        }catch (Exception e){

        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 8080;
        if(args.length>0){
            try{
                port = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
        new WebSocketServer().run(port);
    }
}
