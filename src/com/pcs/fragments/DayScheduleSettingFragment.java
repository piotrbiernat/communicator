package com.pcs.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcs.communicator.R;

public class DayScheduleSettingFragment extends Fragment {

	private TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.day_schedule_settings_view,
				container, false);
		textView = (TextView) view.findViewById(R.id.textView1);
		textView.setText(getArguments().getString("day"));
		return view;
	}

}
