package com.pcs.communicator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.pcs.enums.Day;

public class CalendarQuestionActivity extends Activity {

	public static final String DAY_STRING = "day";
	public static final String TUTOR_MODE = "tutorMode";

	private TextView dayName;
	private boolean isTutorMode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);

		dayName = (TextView) findViewById(R.id.dayName);
		isTutorMode = getIntent().getBooleanExtra(TUTOR_MODE, false);
		Day day = (Day) getIntent().getSerializableExtra(DAY_STRING);
		dayName.setText("Pytania na: "
				+ getResources().getString(day.getResourceID()) + " tutor: "
				+ isTutorMode);

	}
}
