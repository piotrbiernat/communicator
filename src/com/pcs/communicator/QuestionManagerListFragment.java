package com.pcs.communicator;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcs.adapter.QuestionListAdapter;
import com.pcs.database.tables.Question;

public class QuestionManagerListFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO adapter!!!
		//QuestionDAO.getAll();
		ArrayList<Question> list = new ArrayList<Question>();
		Question question = new Question();
		Question question2 = new Question();
		question.setText("pyt1");
		question2.setText("pyt2");
		list.add(question);
		list.add(question2);
		
		setListAdapter(new QuestionListAdapter(list, getActivity() ));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
