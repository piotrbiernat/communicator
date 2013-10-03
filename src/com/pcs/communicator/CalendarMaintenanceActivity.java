package com.pcs.communicator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.pcs.enums.Day;
import com.pcs.fragments.CalendarDetailFragment;
import com.pcs.fragments.CalendarListFragment;

public class CalendarMaintenanceActivity extends FragmentActivity implements
		CalendarListFragment.Callbacks {

	public static final String DAY_STRING = "day";

	private boolean mTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.calender);

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
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putSerializable(CalendarQuestionActivity.DAY_STRING, day);
			CalendarDetailFragment fragment = new CalendarDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.calendar_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					CalendarDetailActivity.class);
			detailIntent.putExtra(CalendarQuestionActivity.DAY_STRING, day);
			startActivity(detailIntent);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.manager_button:

			Intent intent = new Intent(CalendarMaintenanceActivity.this,
					QuestionManagerActivity.class);
			startActivity(intent);
			return true;

		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean isTwoPane() {
		return mTwoPane;
	}
}
