package com.pcs.database.query;

import com.pcs.enums.Day;

public class QueryUtils {

	public static final String genereteQuestionMarks(int numberOfQuestionMarks) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < numberOfQuestionMarks; i++) {
			if (i == 0) {
				buffer.append("?");
			} else {
				buffer.append(", ?");
			}
		}
		return buffer.toString();
	}

	public static final Day getDay(String columnName) {
		if (columnName.equals(QuestionQuery.MONDAY)) {
			return Day.MONDAY;
		}
		if (columnName.equals(QuestionQuery.TUESDAY)) {
			return Day.TUESDAY;
		}
		if (columnName.equals(QuestionQuery.WEDNESDAY)) {
			return Day.WEDNESDAY;
		}
		if (columnName.equals(QuestionQuery.THURSDAY)) {
			return Day.THURSDAY;
		}
		if (columnName.equals(QuestionQuery.FRIDAY)) {
			return Day.FRIDAY;
		}
		if (columnName.equals(QuestionQuery.SUNDAY)) {
			return Day.SUNDAY;
		}
		if (columnName.equals(QuestionQuery.SATURDAY)) {
			return Day.SATURDAY;
		}
		throw new IllegalArgumentException("Such day " + columnName
				+ "is not served");
	}

}
