package com.linkedin.replica.serachEngine.models;

import java.util.ArrayList;

/**
 * Post Model
 */
public class Post {
	private String postId;
	private String authorId;
	private String text;
	private ArrayList<String> images;
	private ArrayList<String> videos;
	private int commentsCount;
	private long timestamp;
	private boolean isCompanyPost;

	private String authorName;
	private String authorProfilePictureUrl;
	private String headLine;
	private ArrayList<Liker> likers;
	private boolean liked;

	public Post() {
	}

	public String getPostId() {
		return postId;
	}

	public String getAuthorId() {
		return authorId;
	}

	public String getText() {
		return text;
	}

	public ArrayList<String> getImages() {
		return images;
	}

	public ArrayList<String> getVideos() {
		return videos;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public boolean isCompanyPost() {
		return isCompanyPost;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getAuthorProfilePictureUrl() {
		return authorProfilePictureUrl;
	}

	public String getHeadLine() {
		return headLine;
	}

	public ArrayList<Liker> getLikers() {
		return likers;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setImages(ArrayList<String> images) {
		this.images = images;
	}

	public void setVideos(ArrayList<String> videos) {
		this.videos = videos;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setCompanyPost(boolean isCompanyPost) {
		this.isCompanyPost = isCompanyPost;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setAuthorProfilePictureUrl(String authorProfilePictureUrl) {
		this.authorProfilePictureUrl = authorProfilePictureUrl;
	}

	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}

	public void setLikers(ArrayList<Liker> likers) {
		this.likers = likers;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Post))
			return false;

		Post post = (Post) obj;

		if (this.postId != null && !this.postId.equals(post.postId))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", authorId=" + authorId + ", text=" + text + ", images=" + images
				+ ", videos=" + videos + ", commentsCount=" + commentsCount + ", timestamp=" + timestamp
				+ ", isCompanyPost=" + isCompanyPost + ", authorName=" + authorName + ", authorProfilePictureUrl="
				+ authorProfilePictureUrl + ", headLine=" + headLine + ", likers=" + likers + ", liked=" + liked + "]";
	}

}
