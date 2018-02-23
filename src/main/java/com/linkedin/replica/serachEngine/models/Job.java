package com.linkedin.replica.serachEngine.models;

/**
 * Job Model
 */
public class Job {
	private String jobID;
	private String industryType;
	private String jobBrief;
	private String title;
	
	public Job(){}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getJobBrief() {
		return jobBrief;
	}

	public void setJobBrief(String jobBrief) {
		this.jobBrief = jobBrief;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
