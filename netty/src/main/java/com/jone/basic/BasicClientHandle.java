package com.jone.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Description
 * @Author zzj
 * @date 2020.04.24
 */
public class BasicClientHandle extends SimpleChannelInboundHandler<ByteBuf> {
    /**客户端读到数据以后就会执行*/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("client accept：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /** 连接建立以后 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
          ctx.writeAndFlush(Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
