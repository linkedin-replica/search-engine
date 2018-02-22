package com.linkedin.replica.serachEngine.models;

/**
 * Post Model
 */
public class Post {
	private String id;
	private String text;
	private String authorId;
	
	public Post(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String txt) {
		this.text = txt;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
}
