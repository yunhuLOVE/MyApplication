package com.augur.zongyang.netty;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private String TAG = "NettyClientHandler";

//    private static final Logger logger = Logger.getLogger(TimeServerHandler.class.getName());

    private int counter;

    private byte[] req;

    private NettyListener listener;

    public NettyClientHandler(NettyListener listener){
        this.listener = listener;
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    /*
    客户端连接到服务端后进行
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
//        for(int i = 0; i < 10; i++) {
//            message = Unpooled.buffer(req.length);
//            message.writeBytes(req);
//            ctx.writeAndFlush(message);
//        }

        NettyClient.getInstance().setConnectStatus(true);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_SUCCESS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyClient.getInstance().setConnectStatus(false);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_CLOSED);
        NettyClient.getInstance().reconnect();
    }

    /*
    客户端读取服务端信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        listener.onMessageResponse(byteBuf);
    }

    /*
    客户端读取服务端信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        listener.onMessageResponse(msg);
        String body = (String) msg;
        // counter的作用是标记这是第几次收到客户端的请求
        System.out.println("Now is : " + body + " ; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.e("TAG","Unexpected exception from downstream : "+cause.getMessage());
        ctx.close();
    }

}
