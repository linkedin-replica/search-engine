package com.linkedin.replica.serachEngine.commands.impl;

import java.util.HashMap;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.database.handlers.DatabaseHandler;
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;

public class SearchCompaniesCommand extends Command {
	public SearchCompaniesCommand(HashMap<String, Object> args, DatabaseHandler dbHandler) {
		super(args, dbHandler);
	}

	@Override
	public Object execute() {
		// get database handler that implements functionality of this command
		SearchHandler dbHandler = (SearchHandler) this.dbHandler;

		// validate that all required arguments that are passed
		validateArgs(new String[] { "searchKey" });

		// call dbHandler to get list of companies from db
		return dbHandler.searchCompanies(args.get("searchKey").toString());
	}
}
