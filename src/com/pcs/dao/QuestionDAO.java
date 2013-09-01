package com.pcs.dao;

import android.content.Context;

import com.pcs.database.tables.Question;
import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.SQLiteDao;
import com.turbomanage.storm.TableHelper;

public class QuestionDAO extends SQLiteDao<Question> {

	Context context;
	
	public QuestionDAO(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long insert(Question obj) {
		return super.insert(obj);
	}
	@Override
	public DatabaseHelper getDbHelper(Context ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableHelper<Question> getTableHelper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void getAll() {
		// TODO Auto-generated method stub
		
	}

}
