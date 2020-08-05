package com.hzy.chatim.server.handler

import com.hzy.chatim.base.MsgHandler
import com.hzy.chatim.manager.ConnectionManager
import io.netty.channel.ChannelHandlerContext
import protobuf.Base

/***
 * 连接消息
 */
class ConnectionMsgHandle : MsgHandler(Base.DataType.TYPE_CONNECT_MSG) {
    override fun handleMsg(msg: Base.BaseMsg, ctx: ChannelHandlerContext) {
        ConnectionManager.refreshChannel(msg.uidId, ctx)
    }

}