package com.linkedin.replica.serachEngine.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.database.handlers.DatabaseHandler;

/**
 * Search Service is responsible for taking input from controller, reading commands config file to 
 * get specific command responsible for handling input request and also get DatabaseHandler name
 * Associated with this command 
 * 
 * It will call command execute method after passing to its DatabaseHandler
 */
public class SearchService {
    private Configuration config = Configuration.getInstance();
		
	public  Object serve(String commandName, HashMap<String, String> args) throws Exception {
        Class<?> dbHandlerClass = config.getHandlerClass(commandName);
        DatabaseHandler dbHandler = (DatabaseHandler) dbHandlerClass.newInstance();

        Class<?> commandClass = config.getCommandClass(commandName);
        Constructor constructor = commandClass.getConstructor(new Class<?>[]{HashMap.class, DatabaseHandler.class});
        Command command = (Command) constructor.newInstance(args,dbHandler);

        return command.execute();
	}
}
