package com.hzy.chatim.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import protobuf.Base
import protobuf.Message
import java.io.IOException

object Client {
    fun connection() {
//新建一个线程 用来发送消息
        val eventLoopGroup: EventLoopGroup = NioEventLoopGroup()
        //客户端的父类
        val bootstrap = Bootstrap()
        //用NioSocketChannel来进行管理通道
        //自定义handler 用来对消息的编码等进行处理 与服务端一致
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .handler(ClientInitializer())
        try {
            val channel = bootstrap.connect("127.0.0.1", 10000).sync().channel()

            var i = 0
            while (i < Int.MAX_VALUE) {
                i++
                Thread.sleep(5000)
                val data = create(i)
                channel.writeAndFlush(data)
                println("send:\n$data")
            }
        } catch (ex: InterruptedException) {
            ex.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
             eventLoopGroup.shutdownGracefully()
        }
    }

    private fun create(i: Int): Base.BaseReq? {
        val build = Base.BaseReq.newBuilder().apply {
            uidId = i.toString()
            deviceId = i.toString()
        }
        if (i == 0) return build.apply {
            val msg = Message.ConnectMessage.newBuilder().apply {
                uid = i.toString()
            }.build()
            connectMsg = msg
            type = Base.DataType.TYPE_CONNECT_MSG
        }.build()
        return when {
            (i % 2 == 0) -> {
                val msg = Message.ChatMessage.newBuilder().apply {
                    uid = i.toString()
                }.build()
                build.apply {
                    imMsg = msg
                    type = Base.DataType.TYPE_IM_MSG
                    println("do:type->$type")
                }.build()
            }
            else -> {
                val msg = Message.HeartBeat.newBuilder().apply {
                    uid = i.toString()
                }.build()
                build.apply {
                    hbMsg = msg
                    type = Base.DataType.TYPE_HB_MSG
                }.build()
            }
        }
    }
}


fun main() {
    Client.connection()
}