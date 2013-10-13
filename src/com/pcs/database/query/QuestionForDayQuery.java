package com.pcs.database.query;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.pcs.database.tables.Question;
import com.pcs.database.tables.QuestionForDay;
import com.pcs.database.tables.dao.QuestionForDayDao;
import com.pcs.database.tables.dao.QuestionForDayTable;
import com.pcs.enums.Day;

public class QuestionForDayQuery extends QuestionForDayDao {

	private AnswersQuery answersQuery;

	public QuestionForDayQuery(Context ctx) {
		super(ctx);
		answersQuery = new AnswersQuery(ctx);
	}

	@Override
	public long insert(QuestionForDay obj) {
		obj.setQuestionOrder(determineMaxOrderNumber(obj.getForDay()) + 1);
		return super.insert(obj);
	}

	@Override
	public long update(QuestionForDay obj) {
		return super.update(obj);
	}

	@Override
	public int delete(Long id) {
		QuestionForDay deletedQuestion = get(id);
		synchronizeOrderNumber(deletedQuestion);
		deleteAssignAnswers(deletedQuestion);
		return super.delete(id);
	}

	public boolean containsQuestionDay(long questionId, Day day) {
		return !findQuestionForDayByQuestionIdAndDay(questionId, day).isEmpty();
	}

	public long saveQuestionForDay(Question question, Day day) {
		QuestionForDay questionForDay = new QuestionForDay();
		questionForDay.setQuestionId(question.getId());
		questionForDay.setForDay(day);
		return insert(questionForDay);
	}

	public List<QuestionForDay> findQuestionForDayByDay(Day day) {
		QuestionForDay exampleObject = new QuestionForDay();
		exampleObject.setForDay(day);
		return listByExample(exampleObject);
	}

	public List<QuestionForDay> findQuestionForDayByQuestionIdAndDay(long questionId, Day day) {
		QuestionForDay exampleObject = new QuestionForDay();
		exampleObject.setQuestionId(questionId);
		exampleObject.setForDay(day);
		return listByExample(exampleObject);
	}

	public List<QuestionForDay> findQuestionForDayByQuestionId(long questionId) {
		QuestionForDay exampleObject = new QuestionForDay();
		exampleObject.setQuestionId(questionId);
		return listByExample(exampleObject);
	}

	public int determineMaxOrderNumber(Day day) {
		String columns[] = { "MAX("
				+ QuestionForDayTable.Columns.QUESTIONORDER.toString() + ") as MAX" };
		String selection = QuestionForDayTable.Columns.FORDAY.toString() +  " = ?";
		String[] selectionArgs = new String [] {day.toString()};
		Cursor c = getReadableDb().query(getTableHelper().getTableName(),
				columns, selection, selectionArgs, null, null, null);
		int result = 0;
		for (boolean hasItem = c.moveToFirst(); hasItem; hasItem = c.moveToNext()) {
			result = c.getInt(c.getColumnIndex("MAX"));
		}

		return result > -1 ? result : 0;
	}
	
	private void synchronizeOrderNumber(QuestionForDay deletedQuestion) {
		for(QuestionForDay questionForDay: findQuestionForDayByQuestionIdAndDay(deletedQuestion.getQuestionId(), deletedQuestion.getForDay())) {
			if (questionForDay.getId() != deletedQuestion.getId()) {
				if (questionForDay.getQuestionOrder() > deletedQuestion.getQuestionOrder()) {
					questionForDay.setQuestionOrder(questionForDay.getQuestionOrder() - 1);
					update(questionForDay);
				}
			}
		}
	}

	private void deleteAssignAnswers(QuestionForDay deletedQuestion) {
		answersQuery.deleteAllAnswersAsociated2QuestionForDay(deletedQuestion);
	}
}
