package com.linkedin.replica.serachEngine.controller;

import java.util.Hashtable;

/**
 * Responsible for validating requests to controller
 */
public class ControllerValidator {
	private static final String controllerURI;
	private static final Hashtable<String, String> htblFuncNames;
	
	static{
		controllerURI = "/api/controller";
		htblFuncNames = new Hashtable<String, String>();
	}
	
	public static boolean isRequestValid(String uri){
		return uri.equalsIgnoreCase(controllerURI);
	}

	private static void populateWithFuncNames(){
		htblFuncNames.put("setmaxthreadcount", "controller.max_thread_count");
		htblFuncNames.put("setmaxthreadcount", "controller.max_thread_count");
		htblFuncNames.put("setmaxthreadcount", "controller.max_thread_count");
		htblFuncNames.put("setmaxthreadcount", "controller.max_thread_count");
		htblFuncNames.put("setmaxthreadcount", "controller.max_thread_count");

	}
}
