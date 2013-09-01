package com.pcs.communicator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.pcs.database.tables.dao.QuestionDao;
import com.pcs.fragments.QuestionManagerListFragment;
import com.pcs.fragments.QuestionManagerListFragment.QuestionManagerActions;
import com.pcs.fragments.QuestionManagerMaintainerFragment;

public class QuestionManagerActivity extends FragmentActivity implements
		QuestionManagerActions {

	private boolean mTwoPane;
	private QuestionDao questionDoa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplicationContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_list);
		questionDoa = new QuestionDao(getApplicationContext());
		if (findViewById(R.id.question_detail_container) != null) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mTwoPane = true;
			// ((QuestionManagerListFragment) getSupportFragmentManager()
			// .findFragmentById(R.id.question_list));
		}
	}

	@Override
	public void addNewQuestion() {
		if (mTwoPane) {
			QuestionManagerMaintainerFragment fragment = new QuestionManagerMaintainerFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.question_detail_container, fragment).commit();
		} else {
			// TODO Dostosowc na telefon
		}
	}

	@Override
	public void editQuestion(long questionId) {
		if (mTwoPane) {
			QuestionManagerMaintainerFragment fragment = new QuestionManagerMaintainerFragment();
			Bundle args = new Bundle();
			args.putLong(QuestionManagerMaintainerFragment.QUESTION_ID,
					questionId);
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.question_detail_container, fragment).commit();
		} else {
			// TODO Dostosowc na telefon
		}
	}

	@Override
	public void deleteQuestion(long questionId) {
		questionDoa.delete(questionId);
		// TODO dodac alert z zapytaniem czy na pewno usunac pytanie
		QuestionManagerListFragment questionList = (QuestionManagerListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.question_list);
		questionList.updateQuestionList();
	}
	
}
