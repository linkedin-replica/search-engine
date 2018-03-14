package com.linkedin.replica.serachEngine.databaseHandlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.database.DatabaseConnection;

public class DatabaseSeed {
	private ArangoDB arangoDB;
	private String dbName;
	
	public DatabaseSeed() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		arangoDB = DatabaseConnection.getInstance().getArangodb();
		dbName = Configuration.getInstance().getArangoConfigProp("db.name");
	}
	
	public void insertPosts() throws IOException, ClassNotFoundException, SQLException{
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/posts"));
		
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.posts.name");	
		
		try{
			arangoDB.db(dbName).createCollection(collectionName);
			
		}catch(ArangoDBException ex){
			// check if exception was raised because that database was not created
			if(ex.getErrorNum() == 1228){
				arangoDB.createDatabase(dbName);
				arangoDB.db(dbName).createCollection(collectionName);
			}else{
				throw ex;
			}
		}
		int counter = 0;
		BaseDocument newDoc;
		
		for(String text : lines){
			newDoc = new BaseDocument();
			newDoc.addAttribute("authorId", counter+"");
			newDoc.addAttribute("text", text);
			arangoDB.db(dbName).collection(collectionName).insertDocument(newDoc);		
			System.out.println("New user document insert with key = " + newDoc.getId());
			counter++;
		}
	}
	
	public void insertCompanies() throws IOException, ClassNotFoundException, SQLException{
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/companies"));
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.companies.name");	

		
		try{
			arangoDB.db(dbName).createCollection(collectionName);
			
		}catch(ArangoDBException ex){
			// check if exception was raised because that database was not created
			if(ex.getErrorNum() == 1228){
				arangoDB.createDatabase(dbName);
				arangoDB.db(dbName).createCollection(collectionName);
			}else{
				throw ex;
			}
		}
		int counter = 0;
		BaseDocument newDoc;
		
		for(String text : lines){
			newDoc = new BaseDocument();
			newDoc.addAttribute("companyID", counter+"");
			newDoc.addAttribute("companyName", text);
			arangoDB.db(dbName).collection(collectionName).insertDocument(newDoc);		
			System.out.println("New companies document insert with key = " + newDoc.getId());
			counter++;
		}
	}

	public void insertJobs() throws IOException, ClassNotFoundException, SQLException{
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/jobs"));
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.jobs.name");	
		
		try{
			arangoDB.db(dbName).createCollection(collectionName);
			
		}catch(ArangoDBException ex){
			// check if exception was raised because that database was not created
			if(ex.getErrorNum() == 1228){
				arangoDB.createDatabase(dbName);
				arangoDB.db(dbName).createCollection(collectionName);
			}else{
				throw ex;
			}
		}
		int counter = 0;
		BaseDocument newDoc;
		
		for(String text : lines){
			newDoc = new BaseDocument();
			newDoc.addAttribute("JobID", counter+"");
			newDoc.addAttribute("title", text);
			arangoDB.db(dbName).collection(collectionName).insertDocument(newDoc);		
			System.out.println("New job document insert with key = " + newDoc.getId());
			counter++;
		}
	}
	
	public void insertUsers() throws IOException, ClassNotFoundException, SQLException{
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/users"));
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.users.name");	
		
		try{
			arangoDB.db(dbName).createCollection(collectionName);
			
		}catch(ArangoDBException ex){
			// check if exception was raised because that database was not created
			if(ex.getErrorNum() == 1228){
				arangoDB.createDatabase(dbName);
			}else{
				throw ex;
			}
		}
		int counter = 0;
		BaseDocument newDoc;
		String[] arr;
		for(String text : lines){
			arr = text.split(" ");
			newDoc = new BaseDocument();
			newDoc.addAttribute("userID", counter+"");
			newDoc.addAttribute("firstName", arr[0]);
			newDoc.addAttribute("lastName", arr[1]);
			arangoDB.db(dbName).collection(collectionName).insertDocument(newDoc);		
			System.out.println("New user document insert with key = " + newDoc.getId());
			counter++;
		}
	}
	
	public void deleteAllPosts() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.posts.name");	
		DatabaseConnection.getInstance().getArangodb().db(dbName).collection(collectionName).drop();
		System.out.println("Post collection is dropped");
	}
	
	public void deleteAllCompanies() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.companies.name");	
		DatabaseConnection.getInstance().getArangodb().db(dbName).collection(collectionName).drop();
		System.out.println("Companies collection is dropped");
	}
	
	public void deleteAllJobs() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.jobs.name");	
		DatabaseConnection.getInstance().getArangodb().db(dbName).collection(collectionName).drop();
		System.out.println("Jobs collection is dropped");
	}
	
	public void deleteAllUsers() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		String collectionName = Configuration.getInstance().getArangoConfigProp("collection.users.name");	
		DatabaseConnection.getInstance().getArangodb().db(dbName).collection(collectionName).drop();
		System.out.println("Users collection is dropped");
	}
	
	public void closeDBConnection() throws ArangoDBException, FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		DatabaseConnection.getInstance().getArangodb().shutdown();	
	}
}
