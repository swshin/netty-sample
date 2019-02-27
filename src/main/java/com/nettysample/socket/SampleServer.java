package com.nettysample.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class SampleServer {

	// 서버 소켓 포트 번호를 지정합니다.
	private int port = 8080;
	private static final int THREADS = 10;

	public SampleServer (int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
	/*
		NioEventLoop는 I/O 동작을 다루는 멀티스레드 이벤트 루프입니다.
		네티는 다양한 이벤트 루프를 제공합니다.
		이 예제에서는 두개의 Nio 이벤트 루프를 사용합니다.
		첫번째 'parent' 그룹은 인커밍 커넥션(incomming connection)을 액세스합니다.
		두번째 'child' 그룹은 액세스한 커넥션의 트래픽을 처리합니다.
		만들어진 채널에 매핑하고 스레드를 얼마나 사용할지는 EventLoopGroup 구현에 의존합니다.
		그리고 생성자를 통해서도 구성할 수 있습니다.
	*/
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			// 서버 부트스트랩을 만듭니다. 이 클래스는 일종의 헬퍼 클래스입니다.
			// 이 클래스를 사용하면 서버에서 Channel을 직접 세팅 할 수 있습니다.
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			// 인커밍 커넥션을 액세스하기 위해 새로운 채널을 객체화 하는 클래스 지정합니다.
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					
					ChannelPipeline pipeline = ch.pipeline();
//					// Decoders
//					 pipeline.addLast("frameDecoder", new LineBasedFrameDecoder(80));
//					 pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
//
//					 // Encoder
//					 pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
					 
					// TODO Auto-generated method stub
					pipeline.addLast(new SampleServerHandler());
				}
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connection.
			ChannelFuture cf = b.bind(port).sync();

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server
			cf.channel().closeFuture().sync();

		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 10021;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		
		new SampleServer(port).run();
	}
	
}
