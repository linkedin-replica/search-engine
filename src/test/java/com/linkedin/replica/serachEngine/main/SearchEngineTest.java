package com.linkedin.replica.serachEngine.main;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDBException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseSeed;
import com.linkedin.replica.serachEngine.models.Company;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.Post;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.services.SearchService;

public class SearchEngineTest {
	private static DatabaseSeed dbSeed;
	private static SearchService service;
	
	@BeforeClass
	public static void setup() throws ClassNotFoundException, IOException, SQLException{
		// startup SearchEngine 
		String[] args = {"src/main/resources/database_config", "src/main/resources/command_config", "src/main/resources/arango_names"};
		SearchEngine.start(args);
		service = new SearchService();
		
		dbSeed = new DatabaseSeed();
		dbSeed.insertUsers();
		dbSeed.insertCompanies();
		dbSeed.insertJobs();
		dbSeed.insertPosts();
		
		
	}
	
	@Test
	public void testSearchUsers() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException{
		System.out.println("er");
		String searchKey = "hm";
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		
		Gson gson = new Gson();
		List<User> results = gson.fromJson( service.serve("search.user",htbl), new TypeToken<List<User>>(){}.getType());
		
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
	public void testSearchCompanies() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException{
		String searchKey = "Goo";
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		
		Gson gson = new Gson();
		List<Company> results = gson.fromJson( service.serve("search.company",htbl), new TypeToken<List<Company>>(){}.getType());
		
		boolean check = false;
		for(Company company : results){
			if(company.getCompanyName().contains(searchKey))
				check = true;
			
			assertEquals("Wrong Fetched Company as its company name does not match search key.", true, check);
			check = false;
		}
	}
	
	@Test
	public void testSearchPosts() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException{
		String searchKey = "Lorem";
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		
		Gson gson = new Gson();
		List<Post> results = gson.fromJson( service.serve("search.post",htbl), new TypeToken<List<Post>>(){}.getType());

		searchKey = searchKey.toLowerCase();
		boolean check = false;
		for(Post post : results){
			
			if(post.getText().toLowerCase().contains(searchKey))
				check = true;

			assertEquals("Wrong Fetched post as its text does not match search key.", true, check);
			check = false;
		}
	}
	
	
	@Test
	public void testSearchJobs() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException{
		String searchKey = "Developer";
		
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		Gson gson = new Gson();
		List<Job> results = gson.fromJson( service.serve("search.job",htbl), new TypeToken<List<Job>>(){}.getType());
		
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
