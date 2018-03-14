package com.linkedin.replica.serachEngine.services;

import com.linkedin.replica.serachEngine.config.Configuration;

public class ControllerService {
	private final static Configuration config = Configuration.getInstance();
	
	public static void setMaxThreadCount(Object val){
		Double maxThreadCount = (Double) val;
		System.out.println(maxThreadCount);
	}
	
	public static void setMaxDBConnectionsCount(Object val){
		Double maxDBConnectionCount = (Double) val;
		System.out.println(maxDBConnectionCount);
	}
	
	public static void addCommand(Object val){
		
	}
	
	public static void deleteCommand(Object val){
		
	}
	
	public static void updateCommand(Object val){
		
	}
	
	public static void updateClass(Object val){
		
	}
	
	public static void freeze(Object val){
		
	}
	
	public static void resume(Object val){
		
	}
	
	public static void setErrorReportingLevel(Object val){
		
	}
}
