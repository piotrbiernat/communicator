package com.pcs.fragments;

import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.pcs.adapter.AssignForDayAdapter;
import com.pcs.adapter.AssignForDayAdapter.CheckDayAction;
import com.pcs.communicator.QuestionManagerActivity;
import com.pcs.communicator.R;
import com.pcs.database.query.QuestionQuery;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.wrappers.QuestionWrapper;
import com.pcs.enums.Day;

public class QuestionManagerMaintainerFragment extends Fragment implements
		CheckDayAction {

	public static final String QUESTION_ID = "questionId";

	private EditText questionContent;
	private Button saveButton;
	private QuestionQuery questionQuery;
	private Question question;
	private boolean isNew;
	private String questionText;
	private ListView assignForDay;

	private class SaveQuestionClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			questionText = questionContent.getText().toString();

			if (!questionText.trim().equals("")) {
				question.setText(questionText);
				saveQuestion();

				isNew = false;
				FragmentActivity a = getActivity();
				QuestionManagerListFragment questionList = (QuestionManagerListFragment) a
						.getSupportFragmentManager().findFragmentById(
								R.id.question_list);
				if (questionList != null)
					questionList.updateQuestionList();
				else
					NavUtils.navigateUpTo(getActivity(), new Intent(
							getActivity(), QuestionManagerActivity.class));
			} else {
				questionContent.setError(getString(R.string.emptyQuestion));
			}
		}

	}

	public void saveQuestion() {
		if (isNew) {
			questionQuery.insert(question);
		} else {
			questionQuery.update(question);
		}
		updateAssignForDayState();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getActivity().getIntent().getExtras();
		questionQuery = new QuestionQuery(getActivity());
		if (getArguments() != null) {
			Long id = getArguments().getLong(QUESTION_ID);
			question = questionQuery.get(id);
			isNew = false;
		} else if (extras != null) { // phone
			Long id = extras.getLong(QUESTION_ID);
			question = questionQuery.get(id);
			isNew = false;
		} else {
			question = new Question();
			isNew = true;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.question_maintain_layout,
				container, false);
		questionContent = (EditText) view.findViewById(R.id.questionContet);
		questionContent.setText(question.getText());

		saveButton = (Button) view.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new SaveQuestionClickListener());

		assignForDay = (ListView) view.findViewById(R.id.assign_for_day);
		assignForDay.setAdapter(new AssignForDayAdapter(getActivity(), this,
				question));
		updateAssignForDayState();
		return view;
	}

	@Override
	public void checkDay(Day day, boolean isChecked) {
		if (isNew) {
			return;
		}
		QuestionWrapper qw = new QuestionWrapper(question);
		if (isChecked) {
			Set<Day> availableDays = qw.getAvailableDays();
			availableDays.add(day);
			qw.setAvailableDays(availableDays);
		} else {
			qw.removeDay(day);
		}
		saveQuestion();
	}

	private void updateAssignForDayState() {
		assignForDay.setEnabled(!isNew);
	}
}
