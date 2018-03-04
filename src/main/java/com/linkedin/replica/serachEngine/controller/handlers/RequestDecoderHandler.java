package com.linkedin.replica.serachEngine.controller.handlers;

import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.models.Request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class RequestDecoderHandler extends ChannelInboundHandlerAdapter{
	StringBuilder builder = new StringBuilder();
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		if(msg instanceof HttpContent){
			HttpContent httpContent = (HttpContent) msg;
			builder.append(httpContent.content().toString(CharsetUtil.UTF_8));
		}
		
		if(msg instanceof LastHttpContent){
			String json = builder.toString();
			Gson gson = new Gson();
			Request request = gson.fromJson(json, Request.class);
			System.out.println(request.toString());
			ctx.write(request);
		}
	}
}
