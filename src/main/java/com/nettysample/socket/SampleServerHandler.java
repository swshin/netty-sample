package com.nettysample.socket;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.nettysample.socket.service.SampleService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class SampleServerHandler extends ChannelInboundHandlerAdapter {
	// 채널을 읽을 때 동작할 코드를 정의 합니다.
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf in = (ByteBuf) msg;
		try {
			// 일부 한글의 경우 깨진다. (ex) 한 
			// telnet 으로 터미널에서 테스트 했을때 ....
			String s = in.toString(CharsetUtil.UTF_8);
			String answer = "Hellow world! msg = " + s;
			System.out.println("answer = " + answer);
			
			SampleService service = new SampleService();
			answer = service.checkWithdrawal(s);
			ctx.write(Unpooled.wrappedBuffer(answer.getBytes(CharsetUtil.UTF_8)));
			ctx.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	// 채널 읽는 것을 완료했을 때 동작할 코드를 정의 합니다.
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush(); // 컨텍스트의 내용을 플러쉬합니다.
	};

	// 예외가 발생할 때 동작할 코드를 정의 합니다.
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace(); // 쌓여있는 트레이스를 출력합니다.
		ctx.close(); // 컨텍스트를 종료시킵니다.
	}
}
