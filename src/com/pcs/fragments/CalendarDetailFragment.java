package com.pcs.fragments;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pcs.adapter.QuestionListAdapter;
import com.pcs.communicator.CalendarDetailActivity;
import com.pcs.communicator.CalendarQuestionActivity;
import com.pcs.communicator.R;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.QuestionDao;
import com.pcs.database.tables.dao.QuestionTable;
import com.pcs.database.tables.wrappers.QuestionWrapper;
import com.pcs.enums.Day;
import com.pcs.fragments.QuestionManagerListFragment.QuestionManagerActions;

/**
 * A fragment representing a single Day detail screen. This fragment is either
 * contained in a {@link DayListActivity} in two-pane mode (on tablets) or a
 * {@link CalendarDetailActivity} on handsets.
 */
public class CalendarDetailFragment extends Fragment implements
		QuestionManagerActions {

	private Day day;
	private QuestionDao questionDao;
	private ListView questionsForAday;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		day = (Day) getArguments().getSerializable(
				CalendarQuestionActivity.DAY_STRING);
		questionDao = new QuestionDao(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_calendar_detail,
				container, false);
		((TextView) rootView.findViewById(R.id.calendar_detail))
				.setText(getActivity().getResources().getString(
						day.getResourceID()));
		questionsForAday = (ListView) rootView
				.findViewById(R.id.questions_for_day);
		List<Question> asList = getAvailableQuestions();
		questionsForAday.setAdapter(new QuestionListAdapter(asList,
				getActivity(), this));

		return rootView;
	}

	private List<Question> getAvailableQuestions() {
		Cursor cursor = questionDao.query(QuestionTable.Columns.AVAILABLEDAYS
				+ " LIKE ?", new String[] { "%" + day.toString() + "%" });
		List<Question> asList = questionDao.asList(cursor);
		return asList;
	}

	@Override
	public void addNewQuestion() {

	}

	@Override
	public void editQuestion(long questionId) {
	}

	@Override
	public void deleteQuestion(long questionId) {

		QuestionWrapper questionWrapper = new QuestionWrapper(
				questionDao.get(questionId));
		questionWrapper.removeDay(day);
		questionDao.update(questionWrapper.getQuestion());
		QuestionListAdapter adapter = (QuestionListAdapter) questionsForAday
				.getAdapter();
		adapter.setQuestions(getAvailableQuestions());
	}

}
