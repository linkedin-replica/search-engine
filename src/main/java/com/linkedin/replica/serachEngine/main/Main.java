package com.linkedin.replica.serachEngine.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.controller.Server;
import com.linkedin.replica.serachEngine.database.DatabaseConnection;

public class Main {
	
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
		Configuration.init(args[0], args[1], args[2]);
		
		// create singleton instance of DatabaseConnection class that is responsible for intiating connections
		// with databases
		DatabaseConnection.init();
	}
	
	public static void start(String... args) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InterruptedException{
		if(args.length != 5)
			throw new IllegalArgumentException("Expected three arguments. 1- app config file path "
					+ "2- database file path  3-   commands path 4- host 5- port");
		
		// create singleton instance of Configuration class that will hold configuration files paths
		Configuration.init(args[0], args[1], args[2]);
		
		// create singleton instance of DatabaseConnection class that is responsible for intiating connections
		// with databases
		DatabaseConnection.init();
		
		// start server
		Server server = new Server(args[3], Integer.parseInt(args[4]));
		server.start();
	}
	
	public static void shutdown() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		DatabaseConnection.getInstance().closeConnections();
	}
	
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, InterruptedException {
		start(args);
	}
}
