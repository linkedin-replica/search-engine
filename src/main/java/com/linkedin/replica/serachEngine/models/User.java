package com.linkedin.replica.serachEngine.models;

/**
 * User Model
 */
public class User {
	private String _key;
	private String firstName;
	private String lastName;
	
	public User(){}
	
	public String get_key() {
		return _key;
	}

	public void set_key(String _key) {
		this._key = _key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
