package com.linkedin.replica.serachEngine.controller.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.linkedin.replica.serachEngine.models.Request;
import com.linkedin.replica.serachEngine.services.SearchService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class RequestProcessingHandler extends ChannelInboundHandlerAdapter{
	private static SearchService service;
	
	public  RequestProcessingHandler() throws FileNotFoundException, IOException {
		super();
		service = new SearchService();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// request object being decoded by RequestDecoderHandler that was registered in channel pipeline before this handler
		Request request = (Request) msg;
		// validate request
		validateRequest(request);
		
		/*
		 *  create arguments hashMap based on type of request. Here the same argument is used for all commands,
		 *  so no need for checking for request type (switch block) and add arguments based on this check.
		 */
		HashMap<String,String> args =  new HashMap<String, String>();
		args.put("searchKey", request.getSearchKey());
		String jsonRes = service.serve(request.getType().getCommandName(), args);
		System.out.println("-->"+jsonRes);
		ctx.writeAndFlush(jsonRes);
	}
	
	/**
	 * Validate request body. For SearchEngine the same key/value pairs are the same for all implemented functionality.
	 * 
	 * @param request
	 */
	private void validateRequest(Request request){
		String arg;
		if(request.getType() == null || request.getSearchKey() == null){
			arg = (request.getType() == null) ? "type" : "searchKey";
			throw new IllegalArgumentException(String.format("Invalid request body. %s key/value is missing", arg));
		}
	}

	/**
	 * Overriding exceptionCaught()  to react to any Throwable.
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		String errorJSON = String.format("\"errMessage\": %s", cause.getMessage());
		ctx.write(errorJSON);
	}
	
	
	
}
