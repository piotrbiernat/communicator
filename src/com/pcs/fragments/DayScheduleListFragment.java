package com.pcs.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.pcs.adapter.DayScheduleListAdapter;
import com.pcs.enums.Day;

public class DayScheduleListFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Day> days = new ArrayList<Day>(5);
		days.add(Day.MONDAY);
		days.add(Day.TUESDAY);
		days.add(Day.WEDNESDAY);
		days.add(Day.THURSDAY);
		days.add(Day.FRIDAY);
		days.add(Day.SATURDAY);
		days.add(Day.SUNDAY);
		setListAdapter(new DayScheduleListAdapter(getActivity(), days));
	}
}
