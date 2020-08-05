package com.hzy.chatim.server.handler

import com.hzy.chatim.base.MsgHandler
import com.hzy.chatim.manager.ConnectionManager
import io.netty.channel.ChannelHandlerContext
import protobuf.Base

/***
 * 心跳消息
 */
class HeartBeatMsgHandler : MsgHandler(Base.DataType.TYPE_HB_MSG) {

    override fun handleMsg(msg: Base.BaseMsg, ctx: ChannelHandlerContext) {
        ConnectionManager.refreshChannel(msg.uidId, ctx)
    }
}