package com.hzy.chatim.manager

import io.netty.channel.ChannelHandlerContext
import java.util.concurrent.ConcurrentHashMap

object ConnectionManager {
    private val channels = ConcurrentHashMap<String, ChannelHandlerContext?>()

    fun sendMsg(uid: String, msg: Any?) {
        if (uid.isBlank()) return
        val channel = channels[uid]
        if (channel?.isRemoved == true) {
            println("client is already remove")
            //TODO 储存到数据库下次拉取
            //TODO 推送平台推送或长链接推送
            return
        }
        //长链接下发
        channel?.writeAndFlush(msg)

    }

    fun refreshChannel(uid: String = "", channel: ChannelHandlerContext?) {
        //在没有接收到首次连接消息时，使用ip标示
        val lastChannel = channels[uid]
        lastChannel?.let {
            if (lastChannel == channel) return@refreshChannel
            //关闭上一个链接
            if (!lastChannel.isRemoved) lastChannel.close()
            channels.remove(uid)
        }
        if (channel?.isRemoved == true) return
        val key = if (uid.isNotBlank()) uid else channel?.channel()?.remoteAddress().toString()
        if (key.isNotBlank()) return
        channels[key] = channel
    }
}