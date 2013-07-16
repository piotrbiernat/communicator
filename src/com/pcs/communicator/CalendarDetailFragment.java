package com.pcs.communicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a single Day detail screen. This fragment is either
 * contained in a {@link DayListActivity} in two-pane mode (on tablets) or a
 * {@link CalendarDetailActivity} on handsets.
 */
public class CalendarDetailFragment extends Fragment {

	public CalendarDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_calendar_detail,
				container, false);
		// Show the dummy content as text in a TextView.
//		if (mItem != null) {
//			((TextView) rootView.findViewById(R.id.calendar_detail))
//					.setText(mItem.content);
//		}
		return rootView;
	}
}
