package com.pcs.communicator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button button;
	private Button calendarButton;

	private class ButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,
					DayScheduleActivity.class);
			startActivity(intent);
		}
	}
	
	private class CalendarButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,
					CalendarListActivity.class);
			startActivity(intent);
		}
	}
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		button = (Button) findViewById(R.id.button_day_schedule);
		button.setOnClickListener(new ButtonOnClickListener());
		
		calendarButton = (Button) findViewById(R.id.button_calender);
		calendarButton.setOnClickListener(new CalendarButtonOnClickListener());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_view, menu);
		return true;
	}

}
