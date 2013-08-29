package com.pcs.communicator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class QuestionManagerActivity extends FragmentActivity {

	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplicationContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_list);

		// TODO tablet
//		if (findViewById(R.id.question_detail_container) != null) {
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//			mTwoPane = true;
//			((CalendarListFragment) getSupportFragmentManager()
//					.findFragmentById(R.id.question_list))
//					.setActivateOnItemClick(true);
//		} 
			
		
			
		
	}
	
}
