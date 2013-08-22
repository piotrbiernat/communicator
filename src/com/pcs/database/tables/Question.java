package com.pcs.database.tables;

import com.turbomanage.storm.api.Entity;

@Entity
public class Question {
	private long id;
	private String text;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
