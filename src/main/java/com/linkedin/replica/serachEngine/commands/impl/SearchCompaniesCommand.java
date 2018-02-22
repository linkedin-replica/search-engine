package com.linkedin.replica.serachEngine.commands.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseHandler;
/**
 *  Implementation of command design patterns for search for companies functionality
 */
public class SearchCompaniesCommand extends Command{


	public SearchCompaniesCommand(HashMap<String, String> args, DatabaseHandler dbHandler) {
		super(args, dbHandler);
	}

	@Override
	public LinkedHashMap<String, Object> execute() {
		// create a LinkedHashMap to hold results 
		LinkedHashMap<String,Object> results = new LinkedHashMap<String, Object>();
		// call dbHandler to get results from db and add returned results to linkedHashMap
		results.put("results", dbHandler.searchCompanies(args.get("searchKey")));
		return results;
	}

}
