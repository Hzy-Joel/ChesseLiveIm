package com.hzy.chatim.base

import com.hzy.chatim.manager.ConnectionManager
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protobuf.Base

val TAG = "ChannelHandler"
open class BaseChannelHandler : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        super.channelRead(ctx, msg)
        if (msg !is Base.BaseMsg) return
        //储存channel
        ConnectionManager.refreshChannel(msg.uidId, ctx)
    }

    /**
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * @param ctx
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        ConnectionManager.refreshChannel(channel = ctx)
        super.channelActive(ctx)
    }





}