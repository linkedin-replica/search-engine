package com.linkedin.replica.serachEngine.commands.impl;

import java.util.List;

import com.google.gson.Gson;
import com.linkedin.replica.serachEngine.commands.Command;
import com.linkedin.replica.serachEngine.models.Company;

/**
 *  Implementation of command design patterns for search for companies functionality
 */
public class SearchCompaniesCommand extends Command{
	
	public SearchCompaniesCommand (){super();}
	
	@Override
	public String execute() {
        // validate that all required arguments that are passed
		validateArgs(new String[]{"searchKey"});
		
		// call dbHandler to get list of companies from db 
		List<Company> companies = dbHandler.searchCompanies(args.get("searchKey"));
		return parseToJSON(companies);
	}

	@Override
	protected String parseToJSON(Object o) {
		List<Company> companies = (List<Company>) o;
		Gson gson = new Gson();
		String json = gson.toJson(companies);
		return json;
	}

}
