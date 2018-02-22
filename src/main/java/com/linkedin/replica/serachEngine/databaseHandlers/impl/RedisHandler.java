package com.linkedin.replica.serachEngine.databaseHandlers.impl;

import java.util.List;

import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseHandler;
import com.linkedin.replica.serachEngine.models.Company;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.models.Post;

/**
 * Implementation of RedisHandler which is responsible for serving specific request from Redis
 */
public class RedisHandler implements DatabaseHandler{

	public List<User> searchUsers(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Company> searchCompanies(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Post> searchPosts(String txt) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Job> searchJobs(String title) {
		// TODO Auto-generated method stub
		return null;
	}
}
