package com.pcs.database.tables;

import com.pcs.enums.Day;
import com.turbomanage.storm.api.Entity;

@Entity
public class Answer {
	private long id;
	private String link;
	private String text;
	private boolean correct;
	private long questionForDayID;
	private Day forDay;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public long getQuestionForDayID() {
		return questionForDayID;
	}

	public void setQuestionForDayID(long questionID) {
		this.questionForDayID = questionID;
	}

	public Day getForDay() {
		return forDay;
	}

	public void setForDay(Day forDay) {
		this.forDay = forDay;
	}
}
