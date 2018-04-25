package com.linkedin.replica.serachEngine.main;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.database.DatabaseConnection;
import com.linkedin.replica.serachEngine.databaseHandlers.DatabaseSeed;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.Post;
import com.linkedin.replica.serachEngine.models.User;
import com.linkedin.replica.serachEngine.services.SearchService;

public class SearchEngineTest {
	private static ArangoDB arangodb;
	private static String dbName;
	private static String usersCollectionName;
	private static String postsCollectionName;
	private static String jobsCollectionName;
	private static SearchService service;
	private static DatabaseSeed databaseSeed;

	@BeforeClass
	public static void setup() throws ClassNotFoundException, IOException, SQLException, InterruptedException {
		// startup SearchEngine
		String[] args = { "src/main/resources/app.config", "src/main/resources/arango.test.config",
				"src/main/resources/commands.config", "src/main/resources/controller.config",
				"src/main/resources/queries.aql" };
		Main.testingStart(args);

		Configuration config = Configuration.getInstance();
		dbName = config.getArangoConfigProp("db.name");
		arangodb = DatabaseConnection.getInstance().getArangodb();

		if (!arangodb.db(dbName).exists())
			arangodb.createDatabase(dbName);

		usersCollectionName = config.getArangoConfigProp("collection.users.name");
		postsCollectionName = config.getArangoConfigProp("collection.posts.name");
		jobsCollectionName = config.getArangoConfigProp("collection.jobs.name");

		String[] collectionsNames = { usersCollectionName, postsCollectionName, jobsCollectionName };

		for (String collectionName : collectionsNames)
			if (!arangodb.db(dbName).collection(collectionName).exists())
				arangodb.db(dbName).createCollection(collectionName);

		databaseSeed = new DatabaseSeed();
		databaseSeed.seed();

		service = new SearchService();
	}

	@Test
	public void testSearchUsers() throws Exception {
		String searchKey = "hm";
		HashMap<String, String> htbl = new HashMap<String, String>();
		htbl.put("searchKey", searchKey);

		List<User> results = (List<User>) service.serve("search.user", htbl);
		
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/users"));
		properties.load(fileInputStream);
		fileInputStream.close();

		Gson gson = new Gson();
		List<User> users = properties.values().stream().map(obj -> {
			return gson.fromJson(obj.toString(), User.class);
		}).filter(user -> user.getFirstName().contains(searchKey) || user.getLastName().contains(searchKey))
				.collect(Collectors.toList());

		assertEquals("Searching for users with a search key = \"" + searchKey + "\"", true,
				results.size() == users.size());

		for (User user : results)
			assertEquals("Searching for users with a search key = \"" + searchKey + "\"", true, users.contains(user));
	}

	@Test
	public void testSearchPosts() throws Exception {
		String searchKey = "Anoth";
		HashMap<String, String> htbl = new HashMap<String, String>();
		htbl.put("searchKey", searchKey);
		htbl.put("userId", "1");

		List<Post> results = (List<Post>) service.serve("search.post", htbl);

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/posts"));
		properties.load(fileInputStream);
		fileInputStream.close();

		Gson gson = new Gson();
		List<Post> posts = properties.values().stream().map(obj -> {
			return gson.fromJson(obj.toString(), Post.class);
		}).filter(post -> post.getText().contains(searchKey)).collect(Collectors.toList());


		for (Post post : results)
			assertEquals("Searching for posts with a search key = \"" + searchKey + "\"", true, posts.contains(post));

	}

	@Test
	public void testSearchJobs() throws Exception {
		String searchKey = "Engineer";

		HashMap<String, String> htbl = new HashMap<String, String>();
		htbl.put("searchKey", searchKey);

		List<Job> results = (List<Job>) service.serve("search.job", htbl);

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/jobs"));
		properties.load(fileInputStream);
		fileInputStream.close();

		Gson gson = new Gson();
		List<Job> jobs = properties.values().stream().map(obj -> {
			return gson.fromJson(obj.toString(), Job.class);
		}).filter(job -> job.getIndustryType().contains(searchKey) || job.getJobTitle().contains(searchKey))
				.collect(Collectors.toList());


		for (Job job : results)
			assertEquals("Searching for jobs with a search key = \"" + searchKey + "\"", true, jobs.contains(job));

	}

	@AfterClass
	public static void tearDown() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException,
			SQLException {
		
		databaseSeed.unSeed();
		Main.shutdown();
	}
}
