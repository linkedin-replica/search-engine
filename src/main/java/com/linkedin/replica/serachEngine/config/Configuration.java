package com.linkedin.replica.serachEngine.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;


public class Configuration {
    private final Properties commandConfig = new Properties();
    private final Properties appConfig = new Properties();
    private final Properties arangoConfig = new Properties();
    private final Properties controllerConfig = new Properties();
    
    private String appConfigPath;
    private String arangoConfigPath;
    private String commandsConfigPath;
    private String controllerConfigPath;
    
    private boolean isAppConfigModified;
    private boolean isArangoConfigModified;
    private boolean isCommandsConfigModified;
//    private boolean isControllerConfigModified;

	private static Configuration instance;
 	
	private Configuration(String appConfigPath, String arangoConfigPath, String commandsConfigPath, String controllerConfigPath) throws IOException {
        populateWithConfig(appConfigPath, appConfig);
        populateWithConfig(arangoConfigPath, arangoConfig);
        populateWithConfig(commandsConfigPath, commandConfig);
        populateWithConfig(controllerConfigPath, controllerConfig);
        
        this.appConfigPath = appConfigPath;
        this.arangoConfigPath = arangoConfigPath;
        this.commandsConfigPath = commandsConfigPath;
        this.controllerConfigPath = controllerConfigPath;
	}
	
	public static Configuration getInstance() {
		return instance;
	}

    public static void init(String appConfigPath, String arangoConfigPath, String commandsConfigPath, String controllerConfigPath) throws IOException {
        instance = new Configuration(appConfigPath, arangoConfigPath, commandsConfigPath, controllerConfigPath);
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

    public String getAppConfigProp(String key) {
        return appConfig.getProperty(key);
    }

    public String getArangoConfigProp(String key) {
        return arangoConfig.getProperty(key);
    }
    
    public String getControllerConfigProp(String key){
    	return controllerConfig.getProperty(key);
    }
    
    public String getCommandConfigProp(String key){
    	return commandConfig.getProperty(key);
    }
    
    public void setAppControllerProp(String key, String val){
    	if(val != null)
    		appConfig.setProperty(key, val);
    	else
    		appConfig.remove(key); // remove property if val is null
    	
    	isAppConfigModified = true;
    }
    
    public void setArrangoConfigProp(String key, String val){
    	if(val != null)
    		arangoConfig.setProperty(key, val);
    	else
    		arangoConfig.remove(key); // remove property if val is null
    	
    	isArangoConfigModified = true;
    }
    
    public void setCommandsConfigProp(String key, String val){
    	if(val != null)
    		commandConfig.setProperty(key, val);
    	else
    		commandConfig.remove(key); // remove property if val is null
    	
    	isCommandsConfigModified = true;
    }
    
    /**
     * Commit changes to write modifications in configuration files
     * @throws IOException
     */
    public void commit() throws IOException{
    	if(isAppConfigModified){
    		writeConfig(appConfigPath, appConfig);
    		isAppConfigModified = false;
    	}
    	
    	if(isArangoConfigModified){
    		writeConfig(arangoConfigPath, arangoConfig);
    		isArangoConfigModified = false;
    	}
    	
    	if(isCommandsConfigModified){
    		writeConfig(commandsConfigPath, commandConfig);
    		isCommandsConfigModified = false;
    	}
    }
    
    private void writeConfig(String filePath, Properties properties) throws IOException{
    	// delete configuration file and then re-write it 
    	Files.deleteIfExists(Paths.get(filePath));
    	OutputStream out = new FileOutputStream(filePath);
    	properties.store(out, "");
    }
}
