package com.linkedin.replica.serachEngine.commands.impl;

import java.util.HashMap;
import java.util.List;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.database.handlers.DatabaseHandler;
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;
import com.linkedin.replica.serachEngine.models.User;

/**
 *  Implementation of command design patterns for search for users functionality
 */
public class SearchUserCommand extends Command{

	public SearchUserCommand(HashMap<String, Object> args, DatabaseHandler dbHandler) {
		super(args, dbHandler);
	}

	@Override
	public Object execute() {
		// get database handler that implements functionality of this command
		SearchHandler dbHandler = (SearchHandler) this.dbHandler;
		
        // validate that all required arguments that are passed
		validateArgs(new String[]{"searchKey"});
		
		// call dbHandler to get list of users from db.
		List<User> users = dbHandler.searchUsers(args.get("searchKey").toString());
		return users;
	}
}
