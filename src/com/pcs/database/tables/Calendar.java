package com.pcs.database.tables;

import com.turbomanage.storm.api.Entity;

@Entity
public class Calendar {
	private long id;
	private long questions;
	private long childID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuestions() {
		return questions;
	}

	public void setQuestions(long questions) {
		this.questions = questions;
	}

	public long getChildID() {
		return childID;
	}

	public void setChildID(long childID) {
		this.childID = childID;
	}
}
