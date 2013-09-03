package com.pcs.database.tables.wrappers;

import java.util.HashSet;
import java.util.Set;

import com.pcs.database.tables.Question;
import com.pcs.enums.Day;

public class QuestionWrapper {

	private Question question;

	public QuestionWrapper(Question question) {
		this.setQuestion(question);
	}

	public void setAvailableDays(Set<Day> days) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Day day : days) {
			stringBuffer.append(day.toString());
		}
		question.setAvailableDays(stringBuffer.toString());
	}

	public Set<Day> getAvailableDays() {
		Set<Day> availableDays = new HashSet<Day>();
		String availableDaysString = question.getAvailableDays() != null ? question
				.getAvailableDays() : "";

		for (Day day : Day.values()) {
			if (availableDaysString.contains(day.toString())) {
				availableDays.add(day);
			}
		}
		return availableDays;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
