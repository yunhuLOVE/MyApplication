package com.augur.zongyang.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 *
 * Created by LiuSaibao on 11/23/2016.
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private NettyListener listener;

    private int WRITE_WAIT_SECONDS = 10;

    private int READ_WAIT_SECONDS = 13;

    public NettyClientInitializer(NettyListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(sslCtx.newHandler(ch.alloc()));    // 开启SSL
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));    // 开启日志，可以设置日志等级
//        pipeline.addLast(new IdleStateHandler(30, 60, 100));

        // 核心在下面两行，加入了LineBasedFrameDecoder和StringDecoder两个解码器
        // 所以当消息到达我们的业务处理handler即TimerServerHandler，所看到的消息
        // 都是前面两个解码器经过处理之后的结果
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        ch.pipeline().addLast(new StringDecoder());
        ch.pipeline().addLast(new StringEncoder());

//        ch.pipeline().addLast("decoder", new StringDecoder());
//        ch.pipeline().addLast("encoder", new StringEncoder());


//        ch.pipeline().addLast(new NettyClientHandler());

        pipeline.addLast(new NettyClientHandler(listener));

    }
}
