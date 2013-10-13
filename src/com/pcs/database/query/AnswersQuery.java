package com.pcs.database.query;

import java.util.List;

import android.content.Context;

import com.pcs.database.tables.Answer;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.QuestionForDay;
import com.pcs.database.tables.dao.AnswerDao;

public class AnswersQuery extends AnswerDao {

	public AnswersQuery(Context ctx) {
		super(ctx);
	}

	public void deleteAllAnswersAsociated2QuestionForDay(QuestionForDay questionForDay) {
		Answer exampleObj = new Answer();
		exampleObj.setQuestionForDayID(questionForDay.getId());
		List<Answer> asociatedAnswers = listByExample(exampleObj);
		for (Answer answer : asociatedAnswers) {
			delete(answer.getId());
		}
	}
	
	public void deleteAllAnswersAsociated2Question(Question question) {
		Answer exampleObj = new Answer();
		exampleObj.setQuestionForDayID(question.getId());
		List<Answer> asociatedAnswers = listByExample(exampleObj);
		for (Answer answer : asociatedAnswers) {
			delete(answer.getId());
		}
	}

	public void deleteAllAnswersAsociated2Question(long questionId) {
		Answer exampleObj = new Answer();
		exampleObj.setQuestionForDayID(questionId);
		List<Answer> asociatedAnswers = listByExample(exampleObj);
		for (Answer answer : asociatedAnswers) {
			delete(answer.getId());
		}
	}

	public void reassignAnswersFromOldQuestion2NewQuestion(Question oldQuesiton, Question newQuestion) {
		Answer exampleObj = new Answer();
		exampleObj.setQuestionForDayID(oldQuesiton.getId());
		List<Answer> asociatedAnswers = listByExample(exampleObj);
		for (Answer answer : asociatedAnswers) {
			answer.setQuestionForDayID(newQuestion.getId());
			update(answer);
		}
	}

}
