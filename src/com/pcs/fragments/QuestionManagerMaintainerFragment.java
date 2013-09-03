package com.pcs.fragments;

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

import com.pcs.communicator.QuestionManagerActivity;
import com.pcs.communicator.R;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.QuestionDao;

public class QuestionManagerMaintainerFragment extends Fragment {

	public static final String QUESTION_ID = "questionId";

	private EditText questionContent;
	private Button saveButton;
	private QuestionDao questionDoa;
	private Question question;
	private boolean isNew;

	private class SaveQuestionClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			question.setText(questionContent.getText().toString());
			if (isNew) {
				questionDoa.insert(question);
			} else {
				questionDoa.update(question);
			}
			isNew = false;
			FragmentActivity a = getActivity();
			QuestionManagerListFragment questionList = (QuestionManagerListFragment) a
					.getSupportFragmentManager().findFragmentById(
							R.id.question_list);
			if (questionList != null)
				questionList.updateQuestionList();
			else
				NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(),
						QuestionManagerActivity.class));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getActivity().getIntent().getExtras();
		questionDoa = new QuestionDao(getActivity());
		if (getArguments() != null) {
			Long id = getArguments().getLong(QUESTION_ID);
			question = questionDoa.get(id);
			isNew = false;
		} else if (extras != null) {	// phone
			Long id = extras.getLong(QUESTION_ID);
			question = questionDoa.get(id);
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
		return view;
	}

}
