package com.pcs.communicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.pcs.fragments.QuestionManagerMaintainerFragment;

/**
 * An activity representing a single Day detail screen. This activity is only
 * used on handset devices.
 */
public class QuestionManagerDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.question_edition);

		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putSerializable(
					CalendarListActivity.DAY_STRING,
					getIntent().getSerializableExtra(
							CalendarListActivity.DAY_STRING));
			QuestionManagerMaintainerFragment fragment = new QuestionManagerMaintainerFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.question_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this,
					QuestionManagerActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			NavUtils.navigateUpTo(this, new Intent(this,
					QuestionManagerActivity.class));
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
