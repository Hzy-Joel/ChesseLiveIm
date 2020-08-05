package com.hzy.chatim.server.handler

import com.hzy.chatim.base.MsgHandler
import com.hzy.chatim.manager.ConnectionManager
import io.netty.channel.ChannelHandlerContext
import protobuf.Base
import protobuf.Message

/***
 * Im消息
 */
class ImMsgHandler : MsgHandler(Base.DataType.TYPE_IM_MSG) {

    override fun handleMsg(msg: Base.BaseMsg, ctx: ChannelHandlerContext) {
        //TODO 根据不同的IM消息做处理 暂时不用 直接转发给对方
//        when (msg.imMsg.type) {
//            Message.ImType.TYPE_TEXT -> {
//
//            }
//        }
        ConnectionManager.sendMsg(msg.imMsg.tid, msg)

    }

}