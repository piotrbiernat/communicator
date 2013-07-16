package com.pcs.communicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CalendarListActivity extends FragmentActivity implements
		CalendarListFragment.Callbacks {

	private boolean mTwoPane = false;
	public static final String DAY_STRING = "day";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_list);

		if (findViewById(R.id.calendar_detail_container) != null) {

			mTwoPane = true;
			((CalendarListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.calendar_list))
					.setActivateOnItemClick(true);
		}
	}

	@Override
	public void onItemSelected(String day) {

		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(DAY_STRING, day);
			CalendarDetailFragment fragment = new CalendarDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.calendar_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CalendarDetailActivity.class);
			detailIntent.putExtra(DAY_STRING, day);
			startActivity(detailIntent);
		}
	}

}
