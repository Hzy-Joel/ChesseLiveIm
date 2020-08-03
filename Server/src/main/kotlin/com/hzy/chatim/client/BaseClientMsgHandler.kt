package com.hzy.chatim.client

import com.hzy.chatim.base.BaseChannelHandler
import com.hzy.chatim.utils.Log
import io.netty.channel.ChannelHandlerContext


class BaseClientMsgHandler : BaseChannelHandler() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        super.channelRead(ctx, msg)
        //客户端接收信息
        Log.i(msg)
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
        Log.i("connection:${ctx.channel().remoteAddress()}")
    }


}