package com.linkedin.replica.serachEngine.commands;

import java.util.HashMap;

import com.linkedin.replica.serachEngine.database.handlers.DatabaseHandler;
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;
import com.linkedin.replica.serachEngine.exceptions.SearchException;
/**
 * Command is an abstract class responsible for handling specific request and it communicates between
 * external input and internal functionality implementation
 */
public abstract class Command {
    protected HashMap<String, Object> args;
    protected DatabaseHandler dbHandler;
    
    public Command(HashMap<String, Object> args, DatabaseHandler dbHandler) {
        this.args = args;
        this.dbHandler = dbHandler;
    }
    
    public Command(HashMap<String, Object> args){
    	this.args = args;
    }
    
    /**
     * Execute the command
     * @return The output (if any) of the command
     * 	LinkedHashMap preserve order of insertion so it will preserve this order when parsing to JSON
     */
    public abstract Object execute() throws Exception;
    
	public void setDbHandler(SearchHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
    
	  protected void validateArgs(String[] requiredArgs) {
	        for(String arg: requiredArgs)
	            if(!args.containsKey(arg)) {
	                String exceptionMsg = String.format("Cannot execute command. %s argument is missing", arg);
	                throw new SearchException(exceptionMsg);
	            }
	    }
}