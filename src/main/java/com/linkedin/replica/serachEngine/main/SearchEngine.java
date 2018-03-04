package com.linkedin.replica.serachEngine.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.config.DatabaseConnection;
import com.linkedin.replica.serachEngine.controller.Server;

public class SearchEngine {
	
	/**
	 * Used for testing when starting netty server is not required
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public static void testingStart(String... args) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		// create singleton instance of Configuration class that will hold configuration files paths
		Configuration.getInstance(args[0], args[1], args[2]);
		
		// create singleton instance of DatabaseConnection class that is responsible for intiating connections
		// with databases
		DatabaseConnection.getInstance();
	}
	
	public static void start(String... args) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InterruptedException{
		if(args.length != 5)
			throw new IllegalArgumentException("Expected three arguments. 1-database_config file path "
					+ "2- command_config file path  3- arango_name file path 4- host 5- port");
		
		// create singleton instance of Configuration class that will hold configuration files paths
		Configuration.getInstance(args[0], args[1], args[2]);
		
		// create singleton instance of DatabaseConnection class that is responsible for intiating connections
		// with databases
		DatabaseConnection.getInstance();
		
		// start server
		Server server = new Server(args[3], Integer.parseInt(args[4]));
		server.start();
	}
	
	public static void shutdown() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		DatabaseConnection.getInstance().closeConnections();
	}
	
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InterruptedException {
		args = new String[] {"src/main/resources/database_config", "src/main/resources/command_config", "src/main/resources/arango_names", "localhost", "8080"};
		SearchEngine.start(args);
	}
}
