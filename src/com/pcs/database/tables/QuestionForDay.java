package com.pcs.database.tables;

import com.pcs.enums.Day;
import com.turbomanage.storm.api.Entity;

@Entity
public class QuestionForDay {
	private long id;
	private long questionId;
	private Day forDay;
	private int questionOrder;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public Day getForDay() {
		return forDay;
	}

	public void setForDay(Day forDay) {
		this.forDay = forDay;
	}

	public int getQuestionOrder() {
		return questionOrder;
	}

	public void setQuestionOrder(int order) {
		this.questionOrder = order;
	}
}
