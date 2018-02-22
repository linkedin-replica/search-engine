package com.linkedin.replica.serachEngine.commands.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseHandler;

/**
 *  Implementation of command design patterns for search for users functionality
 */
public class SearchUserCommand extends Command{

	public SearchUserCommand(HashMap<String, String> args, DatabaseHandler dbHandler) {
		super(args, dbHandler);
	}

	@Override
	public LinkedHashMap<String, Object> execute() {
		// create a LinkedHashMap to hold results 
		LinkedHashMap<String,Object> results = new LinkedHashMap<String, Object>();
		// call dbHandler to get results from db and add returned results to linkedHashMap
		results.put("results", dbHandler.searchUsers(args.get("searchKey")));
		return results;
	}
}
