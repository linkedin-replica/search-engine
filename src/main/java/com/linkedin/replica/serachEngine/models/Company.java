package com.linkedin.replica.serachEngine.models;

/**
 * Company Model
 */
public class Company {
	private String companyID;
	private String companyName;
	private String industryType;
	private String companyProfilePicture;
	private String aboutUs;
	private String website;
	private String adminUserID;
	private long yearFounded;

	public Company() {}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getCompanyProfilePicture() {
		return companyProfilePicture;
	}

	public void setCompanyProfilePicture(String companyProfilePicture) {
		this.companyProfilePicture = companyProfilePicture;
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAdminUserID() {
		return adminUserID;
	}

	public void setAdminUserID(String adminUserID) {
		this.adminUserID = adminUserID;
	}

	public Long getYearFounded() {
		return yearFounded;
	}

	public void setYearFounded(Long yearFounded) {
		this.yearFounded = yearFounded;
	}

}
