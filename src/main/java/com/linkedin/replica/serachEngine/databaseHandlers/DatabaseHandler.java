package com.linkedin.replica.serachEngine.databaseHandlers;

import java.util.List;

import com.linkedin.replica.serachEngine.models.Company;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.models.Post;

/**
 * Database Handler is responsible for dealing with storage engine to handle specific requests
 */
public interface DatabaseHandler {
	
	/**
	 * Search for users 
	 * @return
	 * 	return list of users
	 */
	public List<User> searchUsers();
	
	/**
	 * Search for companies 
	 * @return
	 * 	return list of companies
	 */
	public List<Company> searchCompanies();
	
	/**
	 * Search for posts 
	 * @return
	 * 	return list of posts
	 */
	public List<Post> searchPosts();
	
	/**
	 * Search for jobs 
	 * @return
	 * 	return list of jobs
	 */
	public List<Job> searchJobs();
}
