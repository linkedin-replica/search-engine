package com.linkedin.replica.serachEngine.database.handlers.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.util.MapBuilder;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.database.DatabaseConnection;
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;
import com.linkedin.replica.serachEngine.models.Company;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.models.Post;

/**
 * Implementation of ArangoHandler which is responsible for serving specific
 * request from ArangoDB
 */
public class ArangoSearchHandler implements SearchHandler {
	private ArangoDB arangoDB;
	private String dbName;

	private final Configuration config = Configuration.getInstance();

	public ArangoSearchHandler() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		arangoDB = DatabaseConnection.getInstance().getArangodb();
		dbName = Configuration.getInstance().getArangoConfigProp("db.name");
	}

	/**
	 * Search for users
	 * 
	 * @return return list of users
	 */
	public List<User> searchUsers(String searchKey) {
		// lowerCase name to avoid case sensitive search
		searchKey = searchKey.toLowerCase();

		// get query from queries configuration file
		String query = config.getQuery("search.users.query");

		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().put("searchKey", searchKey).get();

		// execute query
		ArangoCursor<User> cursor = arangoDB.db(dbName).query(query, bindVars, null, User.class);

		// add results to result list and return it
		ArrayList<User> result = new ArrayList<User>();
		cursor.forEachRemaining(user -> {
			result.add(user);
		});

		return result;
	}

	/**
	 * Search for posts
	 * 
	 * @return return list of posts
	 */
	public List<Post> searchPosts(String searchKey, String userId) {
		// lowerCase name to avoid case sensitive search
		searchKey = searchKey.toLowerCase();

		// get query from queries configuration file
		String query = config.getQuery("search.posts.query");

		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().get();
		bindVars.put("searchKey", searchKey);
		bindVars.put("userId", userId);

		// execute query
		ArangoCursor<Post> cursor = arangoDB.db(dbName).query(query, bindVars, null, Post.class);

		// add results to result list and return it
		ArrayList<Post> result = new ArrayList<Post>();
		cursor.forEachRemaining(post -> {
			result.add(post);
		});

		return result;
	}

	/**
	 * Search for jobs
	 * 
	 * @return return list of jobs
	 */
	public List<Job> searchJobs(String searchKey) {
		// lowerCase name to avoid case sensitive search
		searchKey = searchKey.toLowerCase();

		// get query from queries configuration file
		String query = config.getQuery("search.jobs.query");

		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().put("searchKey", searchKey).get();

		// execute query
		ArangoCursor<Job> cursor = arangoDB.db(dbName).query(query, bindVars, null, Job.class);

		// add results to result list and return it
		ArrayList<Job> result = new ArrayList<Job>();
		cursor.forEachRemaining(job -> {
			result.add(job);
		});

		return result;
	}

	/**
	 * Search for companies
	 * 
	 * @return return list of jobs
	 */
	public List<Company> searchCompanies(String searchKey) {
		// lowerCase name to avoid case sensitive search
		searchKey = searchKey.toLowerCase();

		// get query from queries configuration file
		String query = config.getQuery("search.companies.query");
		System.out.println(query);
		
		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().put("searchKey", searchKey).get();

		// execute query
		ArangoCursor<Company> cursor = arangoDB.db(dbName).query(query, bindVars, null, Company.class);

		// add results to result list and return it
		ArrayList<Company> result = new ArrayList<Company>();
		cursor.forEachRemaining(company -> {
			result.add(company);
		});

		return result;
	}
}
