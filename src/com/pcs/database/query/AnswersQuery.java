package com.pcs.database.query;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;

import com.pcs.database.tables.Answer;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.AnswerDao;
import com.pcs.database.tables.dao.AnswerTable;
import com.pcs.enums.Day;

public class AnswersQuery extends AnswerDao {

	public AnswersQuery(Context ctx) {
		super(ctx);
	}

	public void deleteAllAnswersAsociated2Question(Question question) {
		Answer exampleObj = new Answer();
		exampleObj.setQuestionID(question.getId());
		List<Answer> asociatedAnswers = listByExample(exampleObj);
		for (Answer answer : asociatedAnswers) {
			delete(answer.getId());
		}
	}

	public void reassignAnswersFromOldQuestion2NewQuestion(Question oldQuesiton, Question newQuestion) {
		Answer exampleObj = new Answer();
		exampleObj.setQuestionID(oldQuesiton.getId());
		List<Answer> asociatedAnswers = listByExample(exampleObj);
		for (Answer answer : asociatedAnswers) {
			answer.setQuestionID(newQuestion.getId());
			update(answer);
		}
	}

	public SparseArray<List<Answer>> findAnswersForQuestionsAndDay(
			List<Question> questions, Day day) {
		int numQuestion = questions.size();
		String query = AnswerTable.Columns.QUESTIONID + " IN ("
				+ QueryUtils.genereteQuestionMarks(numQuestion) + ") AND "
				+ AnswerTable.Columns.FORDAY + " = ?";
		String[] values = prepareValues(questions, day);
		List<Answer> answers = asList(query(query, values));

		return assignAnswerToQuestion(questions, answers);
	}

	private SparseArray<List<Answer>> assignAnswerToQuestion(
			List<Question> questions, List<Answer> answers) {
		SparseArray<List<Answer>> resultArray = new SparseArray<List<Answer>>();
		for (Question question : questions) {
			resultArray.put((int) question.getId(), new ArrayList<Answer>());
			for (Answer answer : answers) {
				if (resultArray.get((int) answer.getQuestionID()) != null) {
					resultArray.get((int) answer.getQuestionID()).add(answer);
				}
			}
		}
		return resultArray;
	}
	
	private String[] prepareValues(List<Question> questions, Day day) {
		String[] values = new String[questions.size() + 1];
		int row = 0;
		for (Question question : questions) {
			values[row] = Integer.toString((int) question.getId());
			row++;
		}
		values[row] = day.toString();
		return values;
	}
}
