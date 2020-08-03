//package com.hzy.chatim.base
//
//import com.google.protobuf.MessageLite
//import io.netty.buffer.ByteBuf
//import io.netty.channel.ChannelHandlerContext
//import io.netty.handler.codec.MessageToByteEncoder
//import kotlin.experimental.and
//
///***
// * 为了解决数据自适应转换的问题，自定义一个Encoder
// */
//abstract class BaseProtobufEncoder(private val adapter: BaseMsgAdapter) : MessageToByteEncoder<MessageLite>() {
//
//
//    @Throws(Exception::class)
//    override fun encode(ctx: ChannelHandlerContext?, msg: MessageLite, out: ByteBuf) {
//        val body = msg.toByteArray()
//        val header = encodeHeader(msg, body.size.toShort())
//        out.writeBytes(header)
//        out.writeBytes(body)
//        return
//    }
//
//    private fun encodeHeader(msg: MessageLite, bodyLength: Short): ByteArray {
//        val header = ByteArray(4)
//        header[0] = (bodyLength and 0xff).toByte()
//        header[1] = ((bodyLength.toLong() shl 8) and 0xff).toByte()
//        header[2] = 0  // 保留字段段
//        header[3] = getMsgHead(msg)  //消息类型
//        return header
//    }
//
//
//    private fun getMsgHead(msg: MessageLite): Byte {
//        return adapter.getMsgHead(msg)
//    }
//
//
//}