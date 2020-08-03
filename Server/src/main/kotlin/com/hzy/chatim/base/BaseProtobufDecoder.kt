//package com.hzy.chatim.base
//
//import com.google.protobuf.MessageLite
//import io.netty.buffer.ByteBuf
//import io.netty.channel.ChannelHandlerContext
//import io.netty.handler.codec.ByteToMessageDecoder
//import kotlin.experimental.and
//import kotlin.experimental.or
//
//class BaseProtobufDecoder(private val adapter: BaseMsgAdapter) : ByteToMessageDecoder() {
//    @Throws(Exception::class)
//    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf, out: MutableList<Any>) {
//        while (`in`.readableBytes() > 4) { // 如果可读长度小于包头长度，退出。
//            `in`.markReaderIndex()
//            // 获取包头中的body长度
//            val low = `in`.readByte()
//            val high = `in`.readByte()
//            val s0 = (low and 0xff.toByte())
//            var s1 = (high and 0xff.toByte())
//            s1 = (s1.toLong() shl 8).toByte()
//            val length = (s0 or s1).toShort()
//
//            // 获取包头中的protobuf类型
//            `in`.readByte()
//            val dataType = `in`.readByte()
//
//            // 如果可读长度小于body长度，恢复读指针，退出。
//            if (`in`.readableBytes() < length) {
//                `in`.resetReaderIndex()
//                return
//            }
//
//            // 读取body
//            val bodyByteBuf = `in`.readBytes(length.toInt())
//            var array: ByteArray?
//            var offset: Int
//            val readableLen = bodyByteBuf.readableBytes()
//            if (bodyByteBuf.hasArray()) {
//                array = bodyByteBuf.array()
//                offset = bodyByteBuf.arrayOffset() + bodyByteBuf.readerIndex()
//            } else {
//                array = ByteArray(readableLen)
//                bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen)
//                offset = 0
//            }
//
//            //反序列化
//            val result = adapter.parseMsgType(dataType, array, offset, readableLen)
//            result?.let { out.add(result) }
//        }
//    }
//
//}