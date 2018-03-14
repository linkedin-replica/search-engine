package com.linkedin.replica.serachEngine.commands.impl;

import java.util.HashMap;
import java.util.List;

import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.database.handlers.DatabaseHandler;
import com.linkedin.replica.serachEngine.database.handlers.SearchHandler;
import com.linkedin.replica.serachEngine.models.Job;

/**
 *  Implementation of command design patterns for search for jobs functionality
 */
public class SearchJobsCommand extends Command {

	public SearchJobsCommand(HashMap<String, Object> args, DatabaseHandler dbHandler) {
		super(args, dbHandler);
	}

	@Override
	public Object execute() {
		// get database handler that implements functionality of this command
		SearchHandler dbHandler = (SearchHandler) this.dbHandler;
		
        // validate that all required arguments that are passed
		validateArgs(new String[]{"searchKey"});
		
		// call dbHandler to get list of jobs from db 
		List<Job> jobs = dbHandler.searchJobs(args.get("searchKey").toString());
		return jobs;
	}
}
