package com.pcs.communicator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.pcs.communicator.QuestionManagerListFragment.QuestionManagerActions;

public class QuestionManagerActivity extends FragmentActivity implements
		QuestionManagerActions {

	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplicationContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_list);

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

		}
	}

	@Override
	public void editQuestion(long questionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteQuestion(long questionId) {
		// TODO Auto-generated method stub

	}
	
}
