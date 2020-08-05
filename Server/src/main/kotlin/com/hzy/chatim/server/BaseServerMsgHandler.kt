package com.hzy.chatim.server

import com.hzy.chatim.server.handler.MsgHandlerServerManager
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protobuf.Base


class BaseServerMsgHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        super.channelRead(ctx, msg)
        //当客户端发送消息时，服务端可以从该方法上获取到该消息值
        if (msg !is Base.BaseMsg) return
        //责任链派发消息
        ctx?.let { MsgHandlerServerManager.handleMsg(msg, ctx) }
    }

    /**
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * @param ctx
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx)
    }


}