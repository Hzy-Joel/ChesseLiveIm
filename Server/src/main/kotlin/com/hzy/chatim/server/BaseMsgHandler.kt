package com.hzy.chatim.server

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import protobuf.Base
import java.net.InetAddress


class BaseMsgHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        super.channelRead(ctx, msg)
        //当客户端发送消息时，服务端可以从该方法上获取到该消息值
        println("Recieve value is : $msg")
        if (msg !is Base.BaseReq) return
        when (msg.type) {
            Base.DataType.TYPE_HB_MSG -> {
//                println("Recieve TYPE_HB_MSG is : ${msg.hbMsg}")
            }
            Base.DataType.TYPE_CONNECT_MSG -> {
//                println("Recieve TYPE_CONNECT_MSG is : ${msg.connectMsg}")
            }
            Base.DataType.TYPE_IM_MSG -> {
//                println("Recieve TYPE_IM_MSG is : ${msg.imMsg}")
            }
        }
        ctx?.writeAndFlush(msg)
    }

    /**
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * @param ctx
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        println("RemoteAddress : " + ctx.channel().remoteAddress() + " active !")
        println("local Address " + ctx.channel().localAddress())
        // 等同于下一句   ctx.write("");  ctx.flush();
        ctx.writeAndFlush("Welcome to ${InetAddress.getLocalHost().hostName} service!")
        //
        super.channelActive(ctx)
    }


}