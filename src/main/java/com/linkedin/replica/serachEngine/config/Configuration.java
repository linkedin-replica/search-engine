package com.linkedin.replica.serachEngine.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;


public class Configuration {
    private final Properties commandConfig = new Properties();
    private final Properties appConfig = new Properties();
    private final Properties arangoConfig = new Properties();
  
	private static Configuration instance;
 	
	private Configuration(String appConfigPath, String arangoConfigPath, String commandsConfigPath) throws IOException {
        populateWithConfig(appConfigPath, appConfig);
        populateWithConfig(arangoConfigPath, arangoConfig);
        populateWithConfig(commandsConfigPath, commandConfig);
	}
	
	public static Configuration getInstance() {
		return instance;
	}

    public static void init(String appConfigPath, String arangoConfigPath, String commandsConfigPath) throws IOException {
        instance = new Configuration(appConfigPath, arangoConfigPath, commandsConfigPath);
    }
    
    private static void populateWithConfig(String configFilePath, Properties properties) throws IOException {
        FileInputStream inputStream = new FileInputStream(configFilePath);
        properties.load(inputStream);
        inputStream.close();
    }
	
    public Class getCommandClass(String commandName) throws ClassNotFoundException {
        String commandsPackageName = Command.class.getPackage().getName() + ".impl";
        String commandClassPath = commandsPackageName + '.' + commandConfig.get(commandName);
        return Class.forName(commandClassPath);
    }

    public Class getHandlerClass(String commandName) throws ClassNotFoundException {
        String handlerPackageName = SearchHandler.class.getPackage().getName() + ".impl";
        String handlerClassPath = handlerPackageName + "." + commandConfig.get(commandName + ".handler");
        return Class.forName(handlerClassPath);
    }

    public String getAppConfig(String key) {
        return appConfig.getProperty(key);
    }

    public String getArangoConfig(String key) {
        return arangoConfig.getProperty(key);
    }
}
