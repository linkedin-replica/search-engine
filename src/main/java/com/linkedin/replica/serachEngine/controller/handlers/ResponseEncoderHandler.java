package com.linkedin.replica.serachEngine.controller.handlers;

import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.models.ErrorResponseModel;
import com.linkedin.replica.serachEngine.models.SuccessResponseModel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;

public class ResponseEncoderHandler extends ChannelOutboundHandlerAdapter{

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// convert msg (response model from RequestProcessingHandler)
		Gson gson = new Gson();
		String body =  gson.toJson(msg);
		// wrap msg in ByteBuf
		ByteBuf out = Unpooled.wrappedBuffer(body.getBytes());

		
		// construct FullHttpResponse
		FullHttpResponse response = null;
		if(msg instanceof SuccessResponseModel)
			response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK,
					Unpooled.copiedBuffer(out.toString(CharsetUtil.UTF_8), CharsetUtil.UTF_8));
		
		if(msg instanceof ErrorResponseModel)
			response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.BAD_REQUEST,
					Unpooled.copiedBuffer(out.toString(CharsetUtil.UTF_8), CharsetUtil.UTF_8));
		 
		// set headers
	    response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
	    
	    // write response to HttpResponseEncoder 
		ChannelFuture future = ctx.writeAndFlush(response);
		future.addListener(ChannelFutureListener.CLOSE);
	}
	
}
