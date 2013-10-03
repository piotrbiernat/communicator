package com.pcs.communicator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.pcs.database.query.AnswersQuery;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.dao.QuestionDao;
import com.pcs.fragments.ConfirmationDialog;
import com.pcs.fragments.ConfirmationDialog.ConfirmationActions;
import com.pcs.fragments.QuestionManagerListFragment;
import com.pcs.fragments.QuestionManagerListFragment.QuestionManagerActions;
import com.pcs.fragments.QuestionManagerMaintainerFragment;

public class QuestionManagerActivity extends FragmentActivity implements
		QuestionManagerActions, ConfirmationActions {

	private boolean mTwoPane;
	private QuestionDao questionDao;
	private AnswersQuery answerQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplicationContext().getResources();
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.questions_manager);

		setContentView(R.layout.activity_question_list);
		questionDao = new QuestionDao(getApplicationContext());
		answerQuery = new AnswersQuery(this);
		if (findViewById(R.id.question_detail_container) != null) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mTwoPane = true;
		}
	}

	@Override
	public void addNewQuestion() {
		if (mTwoPane) {
			QuestionManagerMaintainerFragment fragment = new QuestionManagerMaintainerFragment();
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
			QuestionManagerMaintainerFragment fragment = new QuestionManagerMaintainerFragment();
			Bundle args = new Bundle();
			args.putLong(QuestionManagerMaintainerFragment.QUESTION_ID,
					questionId);
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.question_detail_container, fragment).commit();
		} else {
			Intent detailIntent = new Intent(this,
					QuestionManagerDetailActivity.class);
			detailIntent.putExtra(
					QuestionManagerMaintainerFragment.QUESTION_ID, questionId);
			startActivity(detailIntent);
		}
	}

	@Override
	public void deleteQuestion(long questionId) {
		Question q = questionDao.get(questionId);
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
			questionDao.delete(questionId);
			answerQuery.deleteAllAnswersAsociated2Question(questionId);
			if (mTwoPane) {
				QuestionManagerListFragment  listFragment = (QuestionManagerListFragment) getSupportFragmentManager().findFragmentById(R.id.question_list);
				listFragment.updateQuestionList();
			}
		}
	}
}
