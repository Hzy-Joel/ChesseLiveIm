package com.hzy.chatim.base

import com.google.protobuf.MessageLite
import protobuf.Base

/***
 * 数据类型适配器，转换消息类型对应的不同字节头部
 */
abstract class BaseMsgAdapter {
    //为不同的数据适配不同字节头
    private var msgToByte = HashMap<Class<*>, Byte>()
    private var byteToMsg = HashMap<Byte, Class<*>>()


    /***
     * 指定返回数据
     */
    abstract fun setMsgType()

    fun getMsgHead(msg: MessageLite): Byte {
        return msgToByte[msg::class.java] ?: 0x0f
    }


    /***
     * 返回对应消息类型字节标示
     */
    private fun addMsgType(block: HashMap<Class<*>, Byte>.() -> Unit) {
        block.invoke(msgToByte)
        addByteType()
    }

    /***
     * 转换字典
     */
    private fun addByteType() {
        msgToByte.forEach { (key, value) ->
            byteToMsg[value] = key
        }
    }

    /***
     * 返回对应消息类型字节标示
     */
    public fun parseMsgType(byte: Byte, array: ByteArray?, offset: Int, length: Int): MessageLite? {
        val cla = byteToMsg[byte]
        if (cla !is MessageLite) return null
        return Base.BaseReq.getDefaultInstance().parserForType.parseFrom(array, offset, length)
    }
}