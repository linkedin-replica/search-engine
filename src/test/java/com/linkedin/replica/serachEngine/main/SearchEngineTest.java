package com.linkedin.replica.serachEngine.main;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDBException;
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
	public static void setup() throws ClassNotFoundException, IOException, SQLException, InterruptedException{
		// startup SearchEngine 
		String[] args = {"src/main/resources/app.config","src/main/resources/arango.test.config", "src/main/resources/commands.config"};
		Main.testingStart(args);
		service = new SearchService();
		
		dbSeed = new DatabaseSeed();
		dbSeed.insertUsers();
		dbSeed.insertCompanies();
		dbSeed.insertJobs();
		dbSeed.insertPosts();
		
		
	}
	
	@Test
	public void testSearchUsers() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String searchKey = "hm";
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		
		List<User> results =  (List<User>) service.serve("search.user",htbl);
		
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
	public void testSearchCompanies() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String searchKey = "Goo";
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		
		List<Company> results = (List<Company>)service.serve("search.company",htbl);
		
		boolean check = false;
		for(Company company : results){
			if(company.getCompanyName().contains(searchKey))
				check = true;
			
			assertEquals("Wrong Fetched Company as its company name does not match search key.", true, check);
			check = false;
		}
	}
	
	@Test
	public void testSearchPosts() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String searchKey = "Lorem";
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		
		List<Post> results =  (List<Post>) service.serve("search.post",htbl);

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
	public void testSearchJobs() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String searchKey = "Developer";
		
		HashMap<String,String> htbl =  new HashMap<String, String>();
		htbl.put("searchKey", searchKey);

		List<Job> results =  (List<Job>) service.serve("search.job",htbl);
		
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
		Main.shutdown();
	}
}
