package com.pcs.communicator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.pcs.enums.Day;
import com.pcs.fragments.CalendarListFragment;

public class CalendarListActivity extends FragmentActivity implements
		CalendarListFragment.Callbacks {

	private boolean mTwoPane = false;
	public static final String DAY_STRING = "day";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplicationContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_list);

		if (findViewById(R.id.calendar_detail_container) != null) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mTwoPane = true;
			((CalendarListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.calendar_list))
					.setActivateOnItemClick(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar_calendar_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onItemSelected(Day day) {

		Intent detailIntent = new Intent(this, CalendarQuestionActivity.class);
		detailIntent.putExtra(DAY_STRING, day);
		startActivity(detailIntent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.manager_button:

			Intent intent = new Intent(CalendarListActivity.this,
					QuestionManagerActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean isTwoPane() {
		return mTwoPane;
	}

}
