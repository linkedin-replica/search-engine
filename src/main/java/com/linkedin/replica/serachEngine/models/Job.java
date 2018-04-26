package com.linkedin.replica.serachEngine.models;

import java.util.ArrayList;

/**
 * Job Model
 */
public class Job {
	private String jobId;
	private String jobTitle;
	private String industryType;
	private String jobBrief;
	private String companyId;
	private ArrayList<String> requiredSkills;
	private String companyName;
	private String profilePictureUrl;

	public Job() {
	}

	public String getJobId() {
		return jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getIndustryType() {
		return industryType;
	}

	public String getJobBrief() {
		return jobBrief;
	}

	public String getCompanyId() {
		return companyId;
	}

	public ArrayList<String> getRequiredSkills() {
		return requiredSkills;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Job))
			return false;
		
		Job job = (Job) obj;
		
		if(this.jobId != null && !this.jobId.equals(job.getJobId()))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", jobTitle=" + jobTitle + ", industryType=" + industryType + ", jobBrief="
				+ jobBrief + ", companyId=" + companyId + ", requiredSkills=" + requiredSkills + ", companyName="
				+ companyName + ", profilePictureUrl=" + profilePictureUrl + "]";
	}
}
