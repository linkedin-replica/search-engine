package com.linkedin.replica.serachEngine.databaseHandlers.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.util.MapBuilder;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.config.DatabaseConnection;
import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseHandler;
import com.linkedin.replica.serachEngine.models.Company;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.models.Post;

/**
 * Implementation of ArangoHandler which is responsible for serving specific request from ArangoDB
 */
public class ArangoHandler implements DatabaseHandler{
	private Properties properties;
	private ArangoDB arangoDB;
	
	public ArangoHandler() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		arangoDB = DatabaseConnection.getInstance().getArangodb();
		properties = new Properties();
		properties.load(new FileInputStream(Configuration.getInstance().getArangoNamesConfigPath()));
	}
	
	/**
	 * Search for users 
	 * @return
	 * 	return list of users
	 */
	public List<User> searchUsers(String name) {
		String dbName = properties.getProperty("db.name");
		String collectionName = properties.getProperty("collection.user.name");		
		
		// lowerCase name to avoid case sensitive search
		name = name.toLowerCase();
		
		// query for getting user if there is a match of his/her firstName or lastName with input
		String query = "FOR t IN "+ collectionName +" FILTER "
				+ "LOWER(t.firstName) LIKE @name || "
				+ "LOWER(t.lastName) LIKE  @name "
				+ "RETURN t";
		
		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().put("name", "%"+name+"%").get();
		
		// execute query
        ArangoCursor<User> cursor = arangoDB.db(dbName).query(query, bindVars, null, User.class);
        
        // add results to result list and return it
        ArrayList<User> result = new ArrayList<User>();
    	cursor.forEachRemaining( user -> { result.add(user); });
		return result;
	}
	
	/**
	 * Search for companies 
	 * @return
	 * 	return list of companies
	 */
	public List<Company> searchCompanies(String name) {
		String dbName = properties.getProperty("db.name");
		String collectionName = properties.getProperty("collection.company.name");
		
		// lowerCase name to avoid case sensitive search
		name = name.toLowerCase();
		
		// query for getting company if there is a match of its companyName with input
		String query = "FOR t IN "+ collectionName +" FILTER "
				+ "LOWER(t.companyName) LIKE @name "
				+ "RETURN t";
		
		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().put("name", "%"+name+"%").get();
		
		// execute query
        ArangoCursor<Company> cursor = arangoDB.db(dbName).query(query, bindVars, null, Company.class);
        // add results to result list and return it
        ArrayList<Company> result = new ArrayList<Company>();
    	cursor.forEachRemaining( company -> { result.add(company); });
		return result;
	}
	
	/**
	 * Search for posts 
	 * @return
	 * 	return list of posts
	 */
	public List<Post> searchPosts(String txt) {
		String dbName = properties.getProperty("db.name");
		String collectionName = properties.getProperty("collection.post.name");
		
		// lowerCase name to avoid case sensitive search
		txt = txt.toLowerCase();
		
		// query for getting user if there is a match of his/her firstName or lastName with input
		String query = "FOR t IN "+ collectionName +" FILTER "
				+ "LOWER(t.text) LIKE @txt "
				+ "RETURN t";
		
		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().put("txt", "%"+txt+"%").get();
		
		// execute query
        ArangoCursor<Post> cursor = arangoDB.db(dbName).query(query, bindVars, null, Post.class);
        
        // add results to result list and return it
        ArrayList<Post> result = new ArrayList<Post>();
    	cursor.forEachRemaining( post -> { result.add(post); });
		return result;
	}
	
	/**
	 * Search for jobs 
	 * @return
	 * 	return list of jobs
	 */
	public List<Job> searchJobs(String title) {
		String dbName = properties.getProperty("db.name");
		String collectionName = properties.getProperty("collection.job.name");
		
		// lowerCase name to avoid case sensitive search
		title = title.toLowerCase();
		
		// query for getting user if there is a match of his/her firstName or lastName with input
		String query = "FOR t IN "+ collectionName +" FILTER "
				+ "LOWER(t.title) LIKE @title "
				+ "RETURN t";
		
		// bind variables in query
		Map<String, Object> bindVars = new MapBuilder().put("title", "%"+title+"%").get();
		
		// execute query
        ArangoCursor<Job> cursor = arangoDB.db(dbName).query(query, bindVars, null, Job.class);
        
        // add results to result list and return it
        ArrayList<Job> result = new ArrayList<Job>();
    	cursor.forEachRemaining( job -> { result.add(job); });
		return result;
	}	
}