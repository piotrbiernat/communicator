package com.pcs.database.query;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.QuestionDao;
import com.pcs.database.tables.dao.QuestionTable;
import com.pcs.database.tables.wrappers.QuestionWrapper;
import com.pcs.enums.Day;

public class QuestionQuery extends QuestionDao {

	public static final String MONDAY = QuestionTable.Columns.MONDAY.toString();
	public static final String TUESDAY = QuestionTable.Columns.TUESDAY.toString();
	public static final String WEDNESDAY = QuestionTable.Columns.WEDNESDAY.toString();
	public static final String THURSDAY = QuestionTable.Columns.THURSDAY.toString();
	public static final String FRIDAY = QuestionTable.Columns.FRIDAY.toString();
	public static final String SUNDAY = QuestionTable.Columns.SUNDAY.toString();
	public static final String SATURDAY = QuestionTable.Columns.SATURDAY.toString();

	private static List<Day> days = new ArrayList<Day>();
	static {
		days.add(Day.MONDAY);
		days.add(Day.TUESDAY);
		days.add(Day.WEDNESDAY);
		days.add(Day.THURSDAY);
		days.add(Day.FRIDAY);
		days.add(Day.SATURDAY);
		days.add(Day.SUNDAY);
	}

	public QuestionQuery(Context ctx) {
		super(ctx);
	}


	public List<Question> findQuestionsForDay(Day day) {
		Cursor cursor = query(QuestionTable.Columns.AVAILABLEDAYS + " LIKE ?",
				new String[] { "%" + day.toString() + "%" });
		return asList(cursor);
	}
	
	public List<Question> findQuestionWithoutDay(Day day) {
		Cursor cursor = query(QuestionTable.Columns.AVAILABLEDAYS
				+ " NOT LIKE ? OR " + QuestionTable.Columns.AVAILABLEDAYS
				+ " is null OR " + QuestionTable.Columns.AVAILABLEDAYS
				+ " = ''",
				new String[] { "%" + day.toString() + "%" });
		return asList(cursor);
	}

	@Override
	public long insert(Question question) {
		synchronizeOrderForDay(question);
		return super.insert(question);
	}

	@Override
	public long update(Question question) {
		synchronizeOrderForDay(question);
		return super.update(question);
	}

	@Override
	public int delete(Long id) {
		synchronizeQuestionsBeforeDelete(id);
		return super.delete(id);
	}

	private void synchronizeQuestionsBeforeDelete(Long deletedQuestionId) {
		Question deletedQuestion = get(deletedQuestionId);
		List<Question> questions = listAll();
		for (Question question : questions) {
			if (question.getId() != deletedQuestionId) {
				boolean updateNeeded = false;
				for (Day day : days) {
					if (checkIfUpdateNeeded(question, deletedQuestion, day)) {
						question.setOrderForDay(question.getOrderForDay(day) - 1 , day);
						updateNeeded = true;
					}
				}
				if(updateNeeded) {
					update(question);
				}
			}
		}

	}

	private boolean checkIfUpdateNeeded(Question question,
			Question deletedQuestion, Day day) {
		return deletedQuestion.getOrderForDay(day) != -1
				&& deletedQuestion.getOrderForDay(day) <= question
						.getOrderForDay(day);
	}

	public void synchronizeOrderForDay(Question question) {
		QuestionWrapper qw = new QuestionWrapper(question);
		String columns[] = { "Max(+" + MONDAY + ") as " + MONDAY,
				"Max(+" + TUESDAY + ") as " + TUESDAY,
				"Max(+" + WEDNESDAY + ") as " + WEDNESDAY,
				"Max(+" + THURSDAY + ") as " + THURSDAY,
				"Max(+" + FRIDAY + ") as " + FRIDAY,
				"Max(+" + SUNDAY + ") as " + SUNDAY,
				"Max(+" + SATURDAY + ") as " + SATURDAY };
		Cursor c = getReadableDb().query(getTableHelper().getTableName(), columns,
				null, null, null,
				null, null);
		for (boolean hasItem = c.moveToFirst(); hasItem; hasItem = c.moveToNext()) {
			question.setMonday(determineOrderNum(c, Day.MONDAY, qw));
			question.setTuesday(determineOrderNum(c, Day.TUESDAY, qw));
			question.setWednesday(determineOrderNum(c, Day.WEDNESDAY, qw));
			question.setThursday(determineOrderNum(c, Day.THURSDAY, qw));
			question.setFriday(determineOrderNum(c, Day.FRIDAY, qw));
			question.setSunday(determineOrderNum(c, Day.SUNDAY, qw));
			question.setSaturday(determineOrderNum(c, Day.SATURDAY, qw));
		}
	}

	private int determineOrderNum(Cursor c, Day day, QuestionWrapper qw) {
		Question q = qw.getQuestion();
		if (qw.getAvailableDays().contains(day)) {
			if (q.getOrderForDay(day) == -1) {
				return c.getInt(c.getColumnIndex(day.toString())) > 0 ? c
						.getInt(c.getColumnIndex(day.toString())) + 1 : 1;
			}
			else {
				return qw.getQuestion().getOrderForDay(day);
			}
		}
		return -1;
	}
}
