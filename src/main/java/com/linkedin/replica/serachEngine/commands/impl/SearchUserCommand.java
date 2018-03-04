package com.linkedin.replica.serachEngine.commands.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseHandler;
import com.linkedin.replica.serachEngine.models.Post;
import com.linkedin.replica.serachEngine.models.User;

/**
 *  Implementation of command design patterns for search for users functionality
 */
public class SearchUserCommand extends Command{

	public SearchUserCommand(){super();}

	@Override
	public String execute() {
        // validate that all required arguments that are passed
		validateArgs(new String[]{"searchKey"});
		
		// call dbHandler to get list of users from db.
		List<User> users = dbHandler.searchUsers(args.get("searchKey"));
		return parseToJSON(users);
	}

	@Override
	protected String parseToJSON(Object o) {
		List<User> users = (List<User>) o;
		Gson gson = new Gson();
		String json = gson.toJson(users);
		return json;
	}
}
