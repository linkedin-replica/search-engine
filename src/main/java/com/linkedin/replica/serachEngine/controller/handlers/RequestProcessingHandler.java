package com.linkedin.replica.serachEngine.controller.handlers;

import java.lang.reflect.Method;
import java.nio.file.InvalidPathException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;

import com.linkedin.replica.serachEngine.Exceptions.SearchException;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.models.ResponseType;
import com.linkedin.replica.serachEngine.services.ControllerService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpResponseStatus;


public class RequestProcessingHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// JSONObject body that was decoded by RequestDecoderHandler
		JSONObject body = (JSONObject) msg;

		// iterate over request JSON body
		Iterator<?> keySetIter = body.keySet().iterator();
		String key, methodName;
		while(keySetIter.hasNext()){
			key = keySetIter.next().toString();
			methodName = getControllerServiceMethodName(key);

			// get method
			Method method = ControllerService.class.getMethod(methodName, Object.class);
			// invoke method, null because it is a static method
			method.invoke(null, body.get(key));
		}
		

		// create successful response
		LinkedHashMap<String, Object> responseBody = new LinkedHashMap<String, Object>();
		responseBody.put("type", ResponseType.SuccessfulResponse);
		responseBody.put("code", HttpResponseStatus.ACCEPTED.code());
		responseBody.put("message", "Changes are applied successfully and configuration files are updated");
		
		// send response to ResponseEncoderHandler
		ctx.writeAndFlush(responseBody);
	}

	/**
	 * Maps requestBodykey to actual method name in controllerService class
	 * 
	 * @param requestBodykey
	 * 	key in JSON body. eg. setMaxThreadCount
	 * @return
	 * 	ControllerService method name
	 */
	public static String getControllerServiceMethodName(String requestBodykey){
		Configuration config = Configuration.getInstance();
		// get mapping configuration key
		String key = config.getControllerConfig("controller.request.body."+requestBodykey.toLowerCase());
		if(key == null)
			throw new SearchException(String.format("Invalid key: %s", requestBodykey));
		
		// ControllerService method name
		return config.getControllerConfig(key);
	}
	
	/**
	 * Overriding exceptionCaught()  to react to any Throwable.
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
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
		
		cause.printStackTrace();
		// send response to ResponseEncoderHandler
		ctx.writeAndFlush(responseBody);
	}	
}
