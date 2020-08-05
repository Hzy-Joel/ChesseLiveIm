package com.hzy.chatim.server.handler

import com.hzy.chatim.base.MsgHandler
import io.netty.channel.ChannelHandlerContext
import protobuf.Base
import java.util.concurrent.CopyOnWriteArrayList

/***
 * 服务端数据链处理管理器
 */
object MsgHandlerServerManager {
    private var handlers: CopyOnWriteArrayList<MsgHandler> = CopyOnWriteArrayList()

    init {
        handlers.apply {
            add(ConnectionMsgHandle())
            add(HeartBeatMsgHandler())
            add(ImMsgHandler())
        }
    }

    fun handleMsg(msg: Base.BaseMsg, ctx: ChannelHandlerContext) {
        val handler = handlers.firstOrNull { it.canHandleMsg(msg.type) }
        handler?.handleMsg(msg, ctx)
    }

}