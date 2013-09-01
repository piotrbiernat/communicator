package com.pcs.communicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.QuestionDao;

public class QuestionManagerMaintainerFragment extends Fragment {

	private EditText questionContent;
	private Button saveButton;
	private QuestionDao questionDoa;

	private class SaveQuestionClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Question newQuestion = new Question();
			newQuestion.setText(questionContent.getText().toString());
			questionDoa.insert(newQuestion);
			FragmentActivity a = getActivity();
			QuestionManagerListFragment questionList = (QuestionManagerListFragment) a
					.getSupportFragmentManager()
					.findFragmentById(R.id.question_list);
			questionList.updateQuestionList();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		questionDoa = new QuestionDao(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.question_maintain_layout,
				container, false);
		questionContent = (EditText) view.findViewById(R.id.questionContet);
		saveButton = (Button) view.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new SaveQuestionClickListener());
		return view;
	}

}
