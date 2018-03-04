package com.linkedin.replica.serachEngine.commands.impl;

import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.Post;

/**
 *  Implementation of command design patterns for search for posts functionality
 */
public class SearchPostsCommand extends Command{

	public SearchPostsCommand(){super();}

	@Override
	public String execute() {
        // validate that all required arguments that are passed
		validateArgs(new String[]{"searchKey"});
		
		// call dbHandler to get list of posts in db
		List<Post> posts = dbHandler.searchPosts(args.get("searchKey"));
		return parseToJSON(posts);
	}

	@Override
	protected String parseToJSON(Object o) {
		List<Post> posts = (List<Post>) o;
		Gson gson = new Gson();
		String json = gson.toJson(posts);
		return json;
	}

}
