package com.linkedin.replica.serachEngine.database.handlers;

import java.util.List;

import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.models.Post;

/**
 * Database Handler is responsible for dealing with storage engine to handle specific requests
 */
public interface SearchHandler extends DatabaseHandler{
	
	/**
	 * Search for users 
	 * @return
	 * 	return list of users
	 */
	public List<User> searchUsers(String searchKey);
	
	/**
	 * Search for posts 
	 * @return
	 * 	return list of posts
	 */
	public List<Post> searchPosts(String searchKey, String userId);
	
	/**
	 * Search for jobs 
	 * @return
	 * 	return list of jobs
	 */
	public List<Job> searchJobs(String searchKey);
}
