package com.linkedin.replica.serachEngine.databaseHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;
import com.linkedin.replica.serachEngine.database.handlers.impl.ArangoSearchHandler;
import com.linkedin.replica.serachEngine.main.Main;
import com.linkedin.replica.serachEngine.models.Job;
import com.linkedin.replica.serachEngine.models.Post;
import com.linkedin.replica.serachEngine.models.User;

import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
	private static ArangoDB arangodb;
	private static String dbName;
	private static String usersCollectionName;
	private static String postsCollectionName;
	private static String jobsCollectionName;
	private static SearchHandler searchHandler;
	private static DatabaseSeed databaseSeed;

	@BeforeClass
	public static void setup() throws ClassNotFoundException, IOException, SQLException, InterruptedException {
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

		searchHandler = new ArangoSearchHandler();
	}

	@Test
	public void testSearchUsers() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		String searchKey = "rg";

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/users"));
		properties.load(fileInputStream);
		fileInputStream.close();

		Gson gson = new Gson();
		List<User> users = properties.values().stream().map(obj -> {
			return gson.fromJson(obj.toString(), User.class);
		}).filter(user -> user.getFirstName().contains(searchKey) || user.getLastName().contains(searchKey))
				.collect(Collectors.toList());

		List<User> results = searchHandler.searchUsers(searchKey);

		assertEquals("Searching for users with a search key = \"" + searchKey + "\"", true,
				results.size() == users.size());

		for (User user : results)
			assertEquals("Searching for users with a search key = \"" + searchKey + "\"", true, users.contains(user));
	}

	@Test
	public void testSearchPosts() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		String searchKey = "LCA";

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/posts"));
		properties.load(fileInputStream);
		fileInputStream.close();

		Gson gson = new Gson();
		List<Post> posts = properties.values().stream().map(obj -> {
			return gson.fromJson(obj.toString(), Post.class);
		}).filter(post -> post.getText().contains(searchKey)).collect(Collectors.toList());

		List<Post> results = searchHandler.searchPosts(searchKey, "1");

		for (Post post : results)
			assertEquals("Searching for posts with a search key = \"" + searchKey + "\"", true, posts.contains(post));

	}

	@Test
	public void testSearchJobs() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		String searchKey = "Edu";

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/jobs"));
		properties.load(fileInputStream);
		fileInputStream.close();

		Gson gson = new Gson();
		List<Job> jobs = properties.values().stream().map(obj -> {
			return gson.fromJson(obj.toString(), Job.class);
		}).filter(job -> job.getIndustryType().contains(searchKey) || job.getJobTitle().contains(searchKey))
				.collect(Collectors.toList());

		List<Job> results = searchHandler.searchJobs(searchKey);
		
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
