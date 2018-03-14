package com.linkedin.replica.serachEngine.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import redis.clients.jedis.Jedis;

import com.arangodb.ArangoDB;
import com.linkedin.replica.serachEngine.config.Configuration;

/**
 *  DatabaseConnection is a singleton class responsible for reading database config file and initiate 
 *  connections to databases
 */
public class DatabaseConnection {
	private ArangoDB arangodb; 
	private Jedis redis;
    private Configuration config;

	private static DatabaseConnection instance;
	
	private DatabaseConnection() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
        config = Configuration.getInstance();
		initializeArangoDB();
//		redis = new Jedis();
	}
	
	/**
	 * To reduce use of synchronization, use  double-checked locking.
	 * we first check to see if an instance is created, and if not, then we synchronize. This
	 * way, we only synchronize the first time which is the initialization phase.
	 * 
	 * @return
	 * 		DatabaseConnection singleton instance
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public static DatabaseConnection getInstance() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{	
		return instance;
	}
	
	public static void init() throws IOException, ClassNotFoundException, SQLException {
		instance = new DatabaseConnection();
	}
	
	/**
	 * Implement the clone() method and throw an exception so that the singleton cannot be cloned.
	 */
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException("DatabaseConnection singleton, cannot be clonned");
	}
	/**
	 * Instantiate ArangoDB
	 * @return
	 */
    private void initializeArangoDB() {
    	arangodb = new ArangoDB.Builder()
                .user(config.getArangoConfigProp("arangodb.user"))
                .password(config.getArangoConfigProp("arangodb.password"))
                .build();
    }
	
	public void closeConnections() throws SQLException{
		if(arangodb != null)
			arangodb.shutdown();
		
		if(redis != null)
			redis.shutdown();
	}
	
	public ArangoDB getArangodb() {
		return arangodb;
	}
	
	public Jedis getRedis() {
		return redis;
	}
}
