package com.pcs.communicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pcs.adapter.QuestionListAdapter;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.QuestionDao;

public class QuestionManagerListFragment extends ListFragment {

	public interface QuestionManagerActions {
		public void addNewQuestion();

		public void editQuestion(long questionId);

		public void deleteQuestion(long questionId);
	}

	private QuestionManagerActions managerActions;
	private QuestionDao questionDoa;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		questionDoa = new QuestionDao(getActivity());
		setListAdapter(new QuestionListAdapter(questionDoa.listAll(),
				getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof QuestionManagerActions)) {
			throw new IllegalStateException(
					"Activity must implement QuestionManagerActions!");
		}
		managerActions = (QuestionManagerActions) activity;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (position == 0) {
			managerActions.addNewQuestion();
		} else if (position > 0 && v.getId() == R.id.deleteAddButton) {
			Question questionToDelete = (Question) getListAdapter().getItem(
					position);
			managerActions.deleteQuestion(questionToDelete.getId());
		} else {
			Question questionToDelete = (Question) getListAdapter().getItem(
					position);
			managerActions.editQuestion(questionToDelete.getId());
		}
		super.onListItemClick(l, v, position, id);
	}

	public void updateQuestionList() {
		setListAdapter(new QuestionListAdapter(questionDoa.listAll(),
				getActivity()));
	}
}
