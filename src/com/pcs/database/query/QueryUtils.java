package com.pcs.database.query;


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
}
