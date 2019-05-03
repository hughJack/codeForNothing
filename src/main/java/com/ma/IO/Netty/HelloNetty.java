package com.ma.IO.Netty;

import com.ma.IO.AIO.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class HelloNetty {

  public static void main(String[] args) {
    new NettyServer(8888).serverStart();
  }
}

//
class NettyServer {


  int port = 8888;

  public NettyServer(int port) {
    this.port = port;
  }

  public void serverStart() {
    EventLoopGroup bossGroup = new NioEventLoopGroup();  // 处理客户端的连接
    EventLoopGroup workerGroup = new NioEventLoopGroup();// 连接完成之后的处理
    // 通过ServerBootstrap来做启动Server前的配置
    ServerBootstrap b = new ServerBootstrap();
    //
    b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class) // 指定客户端连接的通道类型
        .childHandler(new ChannelInitializer<SocketChannel>() {
          // 添加通道的处理器 new Handler()
          // 注册到当前的通道channel上去
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new Handler());
          }
        });

    try {
      ChannelFuture f = b.bind(port).sync();

      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }

  }
}

class Handler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    //super.channelRead(ctx, msg);
    System.out.println("server: channel read");
    ByteBuf buf = (ByteBuf) msg;

    System.out.println(buf.toString(CharsetUtil.UTF_8));

    ctx.writeAndFlush(msg);

    ctx.close();

    //buf.release();
  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    //super.exceptionCaught(ctx, cause);
    cause.printStackTrace();
    ctx.close();
  }
}
