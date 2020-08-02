package com.hzy.chatim.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

object NettyServer {
    fun startServer(){
        //EventLoopGroup 是在4.x版本中提出来的一个新概念。用于channel的管理。服务端需要两个。 一个是boss线程一个是worker线程。
        val eventLoopGroup: EventLoopGroup = NioEventLoopGroup()
        val workerGroup: EventLoopGroup = NioEventLoopGroup()

        try {
            val serverBootstrap = ServerBootstrap()
            serverBootstrap.group(eventLoopGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(ServerInitializer())//这里是自定义的handler，用来初始化通道
            // 服务器绑定端口监听  端口10000 同步接收
            val future = serverBootstrap.bind(10000).sync()
            // 监听服务器关闭监听
            future.channel().closeFuture().sync()
        } catch (ex: InterruptedException) {
        } finally {
            //shutdown
            eventLoopGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

}
