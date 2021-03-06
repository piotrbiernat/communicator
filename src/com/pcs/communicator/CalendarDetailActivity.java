package com.pcs.communicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.pcs.database.tables.dao.QuestionDao;
import com.pcs.fragments.CalendarDetailFragment;

/**
 * An activity representing a single Day detail screen. This activity is only
 * used on handset devices.
 */
public class CalendarDetailActivity extends FragmentActivity {

	QuestionDao questionDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_detail);
		questionDao = new QuestionDao(this);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putSerializable(
					CalendarMaintenanceActivity.DAY_STRING,
					getIntent().getSerializableExtra(
							CalendarMaintenanceActivity.DAY_STRING));
			CalendarDetailFragment fragment = new CalendarDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.calendar_detail_container, fragment).commit();
		}
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

}
