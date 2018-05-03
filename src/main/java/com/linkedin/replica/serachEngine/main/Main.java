package com.linkedin.replica.serachEngine.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import com.linkedin.replica.serachEngine.config.Configuration;
import com.linkedin.replica.serachEngine.controller.Server;
import com.linkedin.replica.serachEngine.database.DatabaseConnection;
import com.linkedin.replica.serachEngine.messaging.MessageReceiver;

public class Main {

	/**
	 * Used for testing when starting netty server is not required
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public static void testingStart(String... args) throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException {
		// create singleton instance of Configuration class that will hold
		// configuration files paths
		Configuration.init(args[0], args[1], args[2], args[3], args[4]);

		// create singleton instance of DatabaseConnection class that is
		// responsible for intiating connections
		// with databases
		DatabaseConnection.init();
	}

	public static void start(String... args) throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException, InterruptedException, TimeoutException {
		if (args.length != 5)
			throw new IllegalArgumentException("Expected three arguments. 1- app config file path \n "
					+ "2- database config file path \n  3- commands config file path \n "
					+ "4- controller config file path \n 5- queries file path");

		// create singleton instance of Configuration class that will hold
		// configuration files paths
		Configuration.init(args[0], args[1], args[2], args[3], args[4]);

		// create singleton instance of DatabaseConnection class that is
		// responsible for intiating connections
		// with databases
		DatabaseConnection.init();
		// start RabbitMQ
		new MessageReceiver();
		// start server
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					new Server().start();
				} catch (InterruptedException e) {
					// TODO logging
				}
			}
		}).start();
	}

	public static void shutdown() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		DatabaseConnection.getInstance().closeConnections();
	}

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException, InterruptedException, TimeoutException {
		String[] arr = { "src/main/resources/app.config", "src/main/resources/arango.config",
				"src/main/resources/commands.config", "src/main/resources/controller.config",
				"src/main/resources/queries.aql" };
		start(arr);
	}
}
