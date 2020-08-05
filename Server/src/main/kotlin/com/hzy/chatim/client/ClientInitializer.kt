package com.hzy.chatim.client

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import protobuf.Base


class ClientInitializer : ChannelInitializer<SocketChannel>() {
    @Throws(Exception::class)
    override fun initChannel(ch: SocketChannel) {
        val channelPipeline = ch.pipeline()
        channelPipeline.addLast(
                ProtobufVarint32FrameDecoder(), //解码时根据首位32字节切割长度
                ProtobufDecoder(Base.BaseMsg.getDefaultInstance()),  //转换实体类
                ProtobufVarint32LengthFieldPrepender(), //编码是 byte 数组头加上实体类的长度
                ProtobufEncoder(), // 实体 转化为 byte数组
                BaseClientMsgHandler() // 自定义处理业务
        )

    }
}