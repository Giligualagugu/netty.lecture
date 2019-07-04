package com.kele.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 模拟netty服务器
 */
public class TestServer {

    public static void main(String[] args) throws InterruptedException {
        //控制组；接受请求，分发
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        //执行组；处理请求内容；
        EventLoopGroup workgroup = new NioEventLoopGroup();
        try {
            //启动服务端的类；
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossgroup, workgroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestInitailizer());//服务初始化器

            ChannelFuture sync = serverBootstrap.bind(8022).sync().channel().closeFuture().sync();

        } finally {
            bossgroup.shutdownGracefully();
            workgroup.shutdownGracefully();
        }
    }
}
