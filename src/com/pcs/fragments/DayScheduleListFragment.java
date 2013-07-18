package com.pcs.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.pcs.adapter.DayScheduleListAdapter;

public class DayScheduleListFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<String> days = new ArrayList<String>(5);
		days.add("Poniedzialek");
		days.add("Wtorek");
		days.add("Sroda");
		days.add("Czwartek");
		days.add("Piatek");
		setListAdapter(new DayScheduleListAdapter(getActivity(), days));
	}
}
