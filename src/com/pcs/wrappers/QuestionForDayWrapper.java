package com.pcs.wrappers;

import com.pcs.database.tables.Question;
import com.pcs.database.tables.QuestionForDay;

public class QuestionForDayWrapper extends QuestionForDay implements Comparable<QuestionForDayWrapper>{

	private Question question;

	public QuestionForDayWrapper(QuestionForDay questionForDay) {
		setId(questionForDay.getId());
		setQuestionId(questionForDay.getQuestionId());
		setQuestionOrder(questionForDay.getQuestionOrder());
		setForDay(questionForDay.getForDay());
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public int compareTo(QuestionForDayWrapper another) {
		return getQuestionOrder() - another.getQuestionOrder();
	}

}
