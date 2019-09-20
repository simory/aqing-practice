//package aqing.io.nio.WebSocket;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.DefaultFullHttpResponse;
//import io.netty.handler.codec.http.FullHttpRequest;
//import io.netty.handler.codec.http.FullHttpResponse;
//import io.netty.handler.codec.http.websocketx.*;
//import io.netty.util.CharsetUtil;
//
//import java.util.Date;
//
//import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
//import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
//
///**
// * @Description:WebSocket服务端处理类
// * @Author: fuqi
// * @Date: 2019/5/14 上午7:16
// */
//public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
//
//    private WebSocketServerHandshaker handshaker;
//
//    public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception{
//        //传统HTTP接入,第一次握手请求消息由HTTP协议承载，处理WebSocket握手请求
//        if(msg instanceof FullHttpRequest){
//            handlerHTTPRequest(ctx, (FullHttpRequest) msg);
//        }
//        //WebSocket接入
//        else if(msg instanceof WebSocketFrame){
//            handlerWebSocketFrame(ctx, (WebSocketFrame)msg);
//        }
//    }
//
//    private void handlerHTTPRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
//        //如果HTTP解码失败，返回HTTP异常；对握手请求消息进行判断，如果heade中不包含upgrade不是websocket，返回400
//        if(!req.getDecoderResult().isSuccess()
//                ||(!"websocket".equals(req.headers().get("Upgrade")))){
//            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
//            return;
//        }
//        //握手处理类，构造握手响应消息返回给客户端，同时将websocket相关编码码动态添加到channelpipeline中，用于websocket消息的编解码
//        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
//                "ws//localhost:8080/websocket", null, false);
//        handshaker = wsFactory.newHandshaker(req);
//        if(handshaker == null){
//            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
//        }else{
//            handshaker.handshake(ctx.channel(),req);
//        }
//    }
//
//    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res){
//        //返回应答给客户端
//        if(res.getStatus().code()!=200){
//            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
//            res.content().writeByte(buf);
//            buf.release();
//            setContentLength(res, res.content().readableBytes());
//        }
//        //如果非Keep-alive，关闭连接
//        ChannelFuture f = ctx.channel().writeAndFlush(res);
//        if(!isKeepAlive(req) || res.getStatus().code()!=200){
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
//    }
//
//
//    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
//        //判断是否是关闭链路的指令
//        if(frame instanceof CloseWebSocketFrame){
//            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
//            return;
//        }
//        //判断是否是ping消息
//        if(frame instanceof PingWebSocketFrame){
//            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
//            return;
//        }
//        //本例程仅支持文本消息，不支持二进制消息
//        if(!(frame instanceof  TextWebSocketFrame)){
//            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
//        }
//        //返回应答消息
//        String request = ((TextWebSocketFrame)frame).text();
//        ctx.channel().write(new TextWebSocketFrame(request+",welcome use Netty Websocket server, now time is"+new Date(request.toString())));
//    }
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
//
//    }
//}
