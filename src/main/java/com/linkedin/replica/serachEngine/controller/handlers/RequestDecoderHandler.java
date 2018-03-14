package com.linkedin.replica.serachEngine.controller.handlers;

import java.nio.file.InvalidPathException;
import java.util.LinkedHashMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.linkedin.replica.serachEngine.Exceptions.SearchException;
import com.linkedin.replica.serachEngine.models.ResponseType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class RequestDecoderHandler extends ChannelInboundHandlerAdapter{
	private StringBuilder builder = new StringBuilder();
	private static final String controllerURI = "/api/controller";

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		/*
		 * check if request is valid
		 */
		if(msg instanceof HttpRequest){
			HttpRequest httpRequest = (HttpRequest) msg;
			if(! httpRequest.uri().equalsIgnoreCase(controllerURI))
				throw new InvalidPathException(httpRequest.uri(), "Access Denied, forbidden request");
		}
			
		/*
		 * HttpContent holds the request body content. A request may have more than HttpContent block so
		 * builder will collect all HttpContents.
		 */
		if(msg instanceof HttpContent){
			HttpContent httpContent = (HttpContent) msg;
			builder.append(httpContent.content().toString(CharsetUtil.UTF_8));
		}
		
		/*
		 * LastHttpContent has trailing headers which indicates the end of request.
		 */
		if(msg instanceof LastHttpContent){
			// decode request body content collected in builder into request object instance.
			String json = builder.toString();
			Gson gson = new Gson();
			JsonObject body = gson.fromJson(json, JsonObject.class);

			// reset builder
			builder = new StringBuilder();
			
			// pass the decoded request to next channel in pipeline
			ctx.fireChannelRead(body);
		}
	}

	/**
	 * Overriding exceptionCaught()  to react to any Throwable.
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// construct Error Response
		LinkedHashMap<String, Object> responseBody = new LinkedHashMap<String, Object>();
		responseBody.put("type", ResponseType.ErrorResponse);
		
		// set Http status code
		if(cause instanceof InvalidPathException)	
			responseBody.put("code", HttpResponseStatus.NOT_FOUND.code());
		else 
			if (cause instanceof SearchException)
				responseBody.put("code", HttpResponseStatus.BAD_REQUEST.code());
			else
				responseBody.put("code", HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
		
		responseBody.put("errMessage", cause.getMessage());
		
		// send response to ResponseEncoderHandler
		ctx.writeAndFlush(responseBody);
	}
}
