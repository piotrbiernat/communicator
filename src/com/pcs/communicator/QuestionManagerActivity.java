package com.pcs.communicator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.pcs.database.query.AnswersQuery;
import com.pcs.database.query.QuestionQuery;
import com.pcs.database.tables.Question;
import com.pcs.fragments.ConfirmationDialog;
import com.pcs.fragments.ConfirmationDialog.ConfirmationActions;
import com.pcs.fragments.QuestionManagerListFragment;
import com.pcs.fragments.QuestionManagerListFragment.QuestionManagerActions;
import com.pcs.fragments.QuestionManagerFragment;

public class QuestionManagerActivity extends FragmentActivity implements
		QuestionManagerActions, ConfirmationActions {

	private boolean mTwoPane;
	private QuestionQuery questionQuery;
	private AnswersQuery answerQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplicationContext().getResources();
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.add_new_question);

		setContentView(R.layout.activity_question_list);
		questionQuery = new QuestionQuery(getApplicationContext());
		answerQuery = new AnswersQuery(this);
		if (findViewById(R.id.question_detail_container) != null) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			QuestionManagerFragment fragment = new QuestionManagerFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.question_detail_container, fragment).commit();
			mTwoPane = true;
		}
	}

	@Override
	public void addNewQuestion() {
		if (mTwoPane) {
			QuestionManagerFragment fragment = new QuestionManagerFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.question_detail_container, fragment).commit();
		} else {
			Intent detailIntent = new Intent(this,
					QuestionManagerDetailActivity.class);
			startActivity(detailIntent);
		}
	}

	@Override
	public void editQuestion(long questionId) {
		if (mTwoPane) {
			QuestionManagerFragment fragment = new QuestionManagerFragment();
			Bundle args = new Bundle();
			args.putLong(QuestionManagerFragment.QUESTION_ID,
					questionId);
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.question_detail_container, fragment).commit();
		} else {
			Intent detailIntent = new Intent(this,
					QuestionManagerDetailActivity.class);
			detailIntent.putExtra(
					QuestionManagerFragment.QUESTION_ID, questionId);
			startActivity(detailIntent);
		}
	}

	@Override
	public void deleteQuestion(long questionId) {
		Question q = questionQuery.get(questionId);
		ConfirmationDialog dialog = new ConfirmationDialog();
		dialog.setOnPositivArgument(questionId);
		dialog.setQuestionText(q.getText());
		dialog.show(getFragmentManager(), "ConfirmationDialog");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this,
					CalendarMaintenanceActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPositiveConfirmation(Object onPositivArgument) {
		if (onPositivArgument instanceof Long) {
			Long questionId = (Long) onPositivArgument;
			questionQuery.delete(questionId);
			answerQuery.deleteAllAnswersAsociated2Question(questionId);
			if (mTwoPane) {
				QuestionManagerListFragment  listFragment = (QuestionManagerListFragment) getSupportFragmentManager().findFragmentById(R.id.question_list);
				QuestionManagerFragment  managerFragment = (QuestionManagerFragment) getSupportFragmentManager().findFragmentById(R.id.question_detail_container);
				managerFragment.clearDialog();
				listFragment.updateQuestionList();
			}
		}
	}
}
