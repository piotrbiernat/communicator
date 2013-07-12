package com.pcs.communicator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CalendarQuestionActivity extends Activity {

	private TextView dayName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
		dayName = (TextView) findViewById(R.id.dayName);
		dayName.setText("Pytania na: " + getIntent().getStringExtra("day"));			
	}
}
