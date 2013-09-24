package com.pcs.database.query;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.QuestionDao;
import com.pcs.database.tables.dao.QuestionTable;
import com.pcs.enums.Day;

public class QuestionQuery extends QuestionDao {

	public QuestionQuery(Context ctx) {
		super(ctx);
	}

	public List<Question> findQuestionForDay(Day day) {
		Cursor cursor = query(QuestionTable.Columns.AVAILABLEDAYS + " LIKE ?",
				new String[] { "%" + day.toString() + "%" });
		return asList(cursor);
	}

}
