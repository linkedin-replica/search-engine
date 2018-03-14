package com.linkedin.replica.serachEngine.controller;

import com.linkedin.replica.serachEngine.config.Configuration;

/**
 * Responsible for validating requests to controller
 */
public class ControllerUtility {
	private static final String controllerURI;
	private static Configuration config;
	static{
		controllerURI = "/api/controller";
		config = Configuration.getInstance();
	}
	
	public static boolean isRequestValid(String uri){
		return uri.equalsIgnoreCase(controllerURI);
	}
}
