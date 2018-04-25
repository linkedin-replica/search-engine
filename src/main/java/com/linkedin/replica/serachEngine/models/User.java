package com.linkedin.replica.serachEngine.models;

/**
 * User Model
 */
public class User {
	private String userId;
	private String firstName;
	private String lastName;
	private String headline;
	private String industry;
	private String profilePictureUrl;

	public User() {
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getHeadline() {
		return headline;
	}

	public String getIndustry() {
		return industry;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;

		User user = (User) obj;

		if (this.userId != null &&!this.userId.equals(user.userId))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", headline="
				+ headline + ", industry=" + industry + ", profilePictureUrl=" + profilePictureUrl + "]";
	}
}
