package com.linkedin.replica.serachEngine.daos;

import java.util.List;

import com.linkedin.replica.serachEngine.models.Company;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.models.Post;

/**
 * Search Data Access Object interface
 */
public interface SearchDao {
	
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
