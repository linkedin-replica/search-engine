package com.linkedin.replica.serachEngine.databaseHandler.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.arangodb.ArangoDB;
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
	ArangoDB arangoDB;
	public ArangoHandler() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		arangoDB = DatabaseConnection.getInstance().getArangodb();
	}
	public List<User> searchUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Company> searchCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Post> searchPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Job> searchJobs() {
		// TODO Auto-generated method stub
		return null;
	}

}
