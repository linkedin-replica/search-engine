package com.linkedin.replica.serachEngine.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.linkedin.replica.serachEngine.config.Configuration;

public class Workers {
    private ExecutorService executor;
    private static volatile Workers instance;
    
    private Workers(){
    	// initialize executor pool to a FixedThreadPool with the number of threads configured 
    	executor = Executors.newFixedThreadPool(Integer.parseInt(Configuration.getInstance().getAppConfigProp("app.max_thread_count")));   	
    }
    
    public static Workers getInstance(){
    	if(instance == null){
    		synchronized (Workers.class) {
				if(instance == null)
					instance = new Workers();
			}
    	}
    	return instance;
    }
    
    public void setNumThreads(int newLimit){
    	((ThreadPoolExecutor)executor).setCorePoolSize(newLimit); //newLimit is new size of the pool 
    }
    
    public void submit(Runnable runnable){
    	executor.submit(runnable);
    }
}
