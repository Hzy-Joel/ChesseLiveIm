package com.hzy.chatim.base

import io.netty.channel.ChannelHandlerContext
import protobuf.Base

/***
 * 责任链节点
 */
abstract class MsgHandler(private val msgType: Base.DataType) {
    abstract fun handleMsg(msg: Base.BaseMsg, ctx: ChannelHandlerContext)
    fun canHandleMsg(passType: Base.DataType) = msgType == passType
}