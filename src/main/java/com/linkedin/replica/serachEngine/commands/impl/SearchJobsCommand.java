package com.linkedin.replica.serachEngine.commands.impl;


import java.util.List;

import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.models.Job;

/**
 *  Implementation of command design patterns for search for jobs functionality
 */
public class SearchJobsCommand extends Command {

	public SearchJobsCommand(){super();}
	
	@Override
	public Object execute() {
        // validate that all required arguments that are passed
		validateArgs(new String[]{"searchKey"});
		
		// call dbHandler to get list of jobs from db 
		List<Job> jobs = dbHandler.searchJobs(args.get("searchKey"));
		return jobs;
	}
}
