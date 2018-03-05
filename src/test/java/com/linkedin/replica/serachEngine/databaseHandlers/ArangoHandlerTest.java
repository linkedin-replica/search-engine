package com.linkedin.replica.serachEngine.databaseHandlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDBException;
import com.linkedin.replica.serachEngine.databaseHandlers.impl.ArangoHandler;
import com.linkedin.replica.serachEngine.main.SearchEngine;
import com.linkedin.replica.serachEngine.models.Company;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.Post;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.services.SearchService;

import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
	private static DatabaseSeed dbSeed;
	
	@BeforeClass
	public static void setup() throws ClassNotFoundException, IOException, SQLException, InterruptedException{
		// startup SearchEngine 
		String[] args = {"src/main/resources/database_config", "src/main/resources/command_config", "src/main/resources/arango_names"};
		SearchEngine.testingStart(args);
		
		dbSeed = new DatabaseSeed();
		dbSeed.insertUsers();
		dbSeed.insertCompanies();
		dbSeed.insertJobs();
		dbSeed.insertPosts();
	}
	
	@Test
	public void testSearchUsers() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String searchKey = "hm";
		DatabaseHandler dbHandler = new ArangoHandler();
		List<User> results = dbHandler.searchUsers(searchKey);
		
		boolean check = false;
		for(User user : results){
			if(user.getFirstName().contains(searchKey))
				check = true;
			
			if(user.getLastName().contains(searchKey))
				check = true;

			assertEquals("Wrong Fetched User as his/her firstName and lastName does not match search key.", true, check);
			check = false;
		}
	}
	
	@Test
	public void testSearchCompanies() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String searchKey = "Goo";
		DatabaseHandler dbHandler = new ArangoHandler();
		List<Company> results = dbHandler.searchCompanies(searchKey);
		
		boolean check = false;
		for(Company company : results){
			if(company.getCompanyName().contains(searchKey))
				check = true;
			
			assertEquals("Wrong Fetched Company as its company name does not match search key.", true, check);
			check = false;
		}
	}
	
	@Test
	public void testSearchPosts() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String searchKey = "Lorem";
		DatabaseHandler dbHandler = new ArangoHandler();
		List<Post> results = dbHandler.searchPosts(searchKey);
		searchKey = searchKey.toLowerCase();
		boolean check = false;
		for(Post post : results){
			System.out.println(post);
			if(post.getText().toLowerCase().contains(searchKey))
				check = true;

			assertEquals("Wrong Fetched post as its text does not match search key.", true, check);
			check = false;
		}
	}
	
	
	@Test
	public void testSearchJobs() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String searchKey = "Developer";
		DatabaseHandler dbHandler = new ArangoHandler();
		List<Job> results = dbHandler.searchJobs(searchKey);
		
		boolean check = false;
		for(Job job : results){
			if(job.getTitle().contains(searchKey))
				check = true;
			
			assertEquals("Wrong Fetched Job as its title does not match search key.", true, check);
			check = false;
		}
	}
	
	@AfterClass
	public static void tearDown() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		dbSeed.deleteAllUsers();
		dbSeed.deleteAllCompanies();
		dbSeed.deleteAllJobs();
		dbSeed.deleteAllPosts();
		SearchEngine.shutdown();
	}
	
}
