package com.pcs.communicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DayScheduleActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_schedule_list_view);
	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.day_schedule_menu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getItemId() == R.id.day_schedule_settings) {
//			Intent intent = new Intent(this, DayScheduleSettingActivity.class);
//			this.startActivity(intent);
//			return true;
//		}
//		return false;
//	}

}
