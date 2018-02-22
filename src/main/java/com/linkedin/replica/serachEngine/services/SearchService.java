package com.linkedin.replica.serachEngine.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseHandler;

/**
 * Search Service is responsible for taking input from controller, reading commands config file to 
 * get specific command responsible for handling input request and also get DatabaseHandler name
 * Associated with this command 
 * 
 * It will call command execute method after passing to its DatabaseHandler
 */
public class SearchService {
	// load config file
	private Properties prop;
	private String commandsPackageName;
	private String dbHandlerPackageName;
	
	public SearchService() throws FileNotFoundException, IOException{
		prop = new Properties();
		prop.load(new FileInputStream(Configuration.getInstance().getCommandConfigPath()));
		commandsPackageName = "com.linkedin.replica.serachEngine.commands.impl";
		dbHandlerPackageName = "com.linkedin.replica.serachEngine.databaseHandler.impl";
	}
		
	public  LinkedHashMap<String, Object> serve(String commandName, HashMap<String, String> args) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
			String commandClassName = commandsPackageName + "." + prop.getProperty(commandName + ".command");
			String handlerClassName = dbHandlerPackageName + "." + prop.getProperty(commandName+ ".dbHandler");
			
			// load class of type command and create an instance
			Class c = Class.forName(commandClassName);
			Object o = c.newInstance();
			Command command = (Command) o;
			
			// load class of type database handler
			c = Class.forName(handlerClassName);
			o = c.newInstance();
			DatabaseHandler dbHandler = (DatabaseHandler) o;
			
			// set args and dbHandler of command
			command.setArgs(args);
			command.setDbHandler(dbHandler);
			
			// execute command
			return command.execute();
	}
}
