package com.linkedin.replica.serachEngine.commands;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseHandler;
/**
 * Command is an abstract class responsible for handling specific request and it communicates between
 * external input and internal functionality implementation
 */
public abstract class Command {
    protected HashMap<String, String> args;
    protected DatabaseHandler dbHandler;
    
    public Command(HashMap<String, String> args, DatabaseHandler dbHandler) {
        this.args = args;
        this.dbHandler = dbHandler;
    }

    /**
     * Execute the command
     * @return The output (if any) of the command
     * 	LinkedHashMap preserve order of insertion so it will preserve this order when parsing to JSON
     */
    public abstract LinkedHashMap<String, Object> execute();
}