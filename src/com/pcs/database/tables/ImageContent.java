package com.pcs.database.tables;

import com.turbomanage.storm.api.Entity;

@Entity
public class ImageContent {
	private long id;
	private String imageLink;
	private String description;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
