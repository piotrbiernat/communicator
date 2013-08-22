package com.pcs.communicator;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.pcs.enums.Day;

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
		ActionBar ab = getActionBar();
		LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
		View view = inflater.inflate(R.layout.action_bar_calendar_activity,
				null);
		ab.setCustomView(view);
		ab.setDisplayShowCustomEnabled(true);
		return true;
	}

	@Override
	public void onItemSelected(Day day) {

		Intent detailIntent = new Intent(this, CalendarQuestionActivity.class);
		detailIntent.putExtra(DAY_STRING, day);
		startActivity(detailIntent);
	}

	public boolean isTwoPane() {
		return mTwoPane;
	}

}
