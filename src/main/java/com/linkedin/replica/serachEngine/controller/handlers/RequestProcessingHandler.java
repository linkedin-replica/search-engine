package com.linkedin.replica.serachEngine.controller.handlers;

import java.util.HashMap;
import com.linkedin.replica.serachEngine.models.Request;
import com.linkedin.replica.serachEngine.services.SearchService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class RequestProcessingHandler extends ChannelInboundHandlerAdapter{
	private static SearchService service;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Request request = (Request) msg;
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", request.getSearchKey());
		String jsonRes = service.serve(request.getType().getCommandName(), htbl);
		ctx.write(jsonRes);
	}
}
