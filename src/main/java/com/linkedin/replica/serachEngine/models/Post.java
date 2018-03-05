package com.linkedin.replica.serachEngine.models;

/**
 * Post Model
 */
public class Post {
	private String _key;
	private String text;
	private String authorId;
	
	public Post(){}
	
	public String get_key() {
		return _key;
	}

	public void set_key(String _key) {
		this._key = _key;
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
