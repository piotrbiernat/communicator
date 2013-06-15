package com.pcs.communicator;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class DayScheduleSettingActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preferences, target);
	}
}
