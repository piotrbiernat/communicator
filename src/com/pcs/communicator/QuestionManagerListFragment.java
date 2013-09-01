package com.pcs.communicator;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcs.adapter.QuestionListAdapter;
import com.pcs.dao.QuestionDAO;
import com.pcs.database.tables.Question;

public class QuestionManagerListFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO adapter!!!
		//QuestionDAO.getAll();
		List<Question> list;
		QuestionDAO dao = new QuestionDAO(getActivity());
		dao.insert(new Question("pyt1"));
		dao.insert(new Question("pyt2"));
		list = dao.listAll();
				
		setListAdapter(new QuestionListAdapter(list, getActivity() ));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
