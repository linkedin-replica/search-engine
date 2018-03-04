package com.linkedin.replica.serachEngine.models;

public enum RequestType {
	USERS("search.user"), 
	COMPANIES("search.company"), 
	JOBS("search.post"), 
	POSTS("search.job");
	
	private String commandName;
	
	private RequestType(String commandName){
		this.commandName = commandName;
	}
	
	public String getCommandName(){
		return commandName;
	}
}
