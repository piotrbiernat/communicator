package com.pcs.communicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcs.enums.Day;

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
		Day day = (Day) getArguments().getSerializable(
				CalendarQuestionActivity.DAY_STRING);
		((TextView) rootView.findViewById(R.id.calendar_detail))
				.setText(getActivity().getResources().getString(
						R.string.title_calendar_detail)
						+ " "
						+ getActivity().getResources().getString(
								day.getResourceID()));
		return rootView;
	}
}
