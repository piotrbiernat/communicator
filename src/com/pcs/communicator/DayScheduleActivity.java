package com.pcs.communicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class DayScheduleActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.day_schedule);
		setContentView(R.layout.day_schedule_list_view);
	}

	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.day_schedule_menu, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// if (item.getItemId() == R.id.day_schedule_settings) {
	// Intent intent = new Intent(this, DayScheduleSettingActivity.class);
	// this.startActivity(intent);
	// return true;
	// }
	// return false;
	// }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
