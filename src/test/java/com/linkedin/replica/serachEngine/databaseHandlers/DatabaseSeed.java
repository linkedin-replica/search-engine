package com.linkedin.replica.serachEngine.databaseHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.database.DatabaseConnection;
import com.linkedin.replica.serachEngine.models.Post;

public class DatabaseSeed {
	private ArangoDB arangoDB;
	private String dbName;
	private final  Properties properties = new Properties();
	
	public DatabaseSeed() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		arangoDB = DatabaseConnection.getInstance().getArangodb();
		dbName = Configuration.getInstance().getArangoConfigProp("db.name");
	}
	
	private void insertPosts() throws IOException, ClassNotFoundException, SQLException{		
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/postsSeed.aql"));
		properties.load(fileInputStream);
		fileInputStream.close();
		String query = properties.getProperty("seed.posts.query");
		arangoDB.db(dbName).query(query, null, null, null);
	}
	
	private void insertJobs() throws IOException, ClassNotFoundException, SQLException{
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/jobsSeed.aql"));
		properties.load(fileInputStream);
		fileInputStream.close();
		String query = properties.getProperty("seed.jobs.query");
		arangoDB.db(dbName).query(query, null, null, null);
	}
	
	private void insertUsers() throws IOException, ClassNotFoundException, SQLException{
		FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/usersSeed.aql"));
		properties.load(fileInputStream);
		fileInputStream.close();
		String query = properties.getProperty("seed.users.query");
		arangoDB.db(dbName).query(query, null, null, null);
	}
	
	private void deleteAllPosts() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.posts.name");	
		DatabaseConnection.getInstance().getArangodb().db(dbName).collection(collectionName).drop();
	}
	
	private void deleteAllJobs() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.jobs.name");	
		DatabaseConnection.getInstance().getArangodb().db(dbName).collection(collectionName).drop();
	}
	
	private void deleteAllUsers() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.users.name");	
		DatabaseConnection.getInstance().getArangodb().db(dbName).collection(collectionName).drop();
	}
	
	public void seed() throws ClassNotFoundException, IOException, SQLException{
		insertJobs();
		insertPosts();
		insertUsers();
	}
	
	public void unSeed() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		deleteAllJobs();
		deleteAllPosts();
		deleteAllUsers();
	}
	
	public void closeDBConnection() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		DatabaseConnection.getInstance().getArangodb().shutdown();	
	}
	
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/test"));
		List<String> newLines = new ArrayList<String>();
		
		int i=1;
		boolean isNewObj = true;
		for(String line : lines){
			if(isNewObj && line.trim().equals("{")){
				newLines.add(i++ +"= \\"); 
				isNewObj = false;
			}
			if(line.trim().equals("}==")){
				newLines.add(line.replaceAll("=", "")); 
				isNewObj = true;
			}
			else
				newLines.add(line+" \\"); 
		}
		Files.write(Paths.get("src/test/resources/out"), newLines, StandardOpenOption.CREATE_NEW);
		
//		Properties properties = new Properties();
//		properties.load(new FileInputStream(new File("src/test/resources/posts")));
//		System.out.println(properties.getProperty("1"));
//		System.out.println(new Gson().fromJson(properties.getProperty("1"), Post.class));
	}
}
