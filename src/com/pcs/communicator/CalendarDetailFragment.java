package com.pcs.communicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pcs.enums.Day;

/**
 * A fragment representing a single Day detail screen. This fragment is either
 * contained in a {@link DayListActivity} in two-pane mode (on tablets) or a
 * {@link CalendarDetailActivity} on handsets.
 */
public class CalendarDetailFragment extends Fragment {

	private Day day;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		day = (Day) getArguments().getSerializable(
				CalendarQuestionActivity.DAY_STRING);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_calendar_detail,
				container, false);
		((TextView) rootView.findViewById(R.id.calendar_detail))
				.setText(getActivity().getResources().getString(
						R.string.title_calendar_detail)
						+ " "
						+ getActivity().getResources().getString(
								day.getResourceID()));
		ListView questionsForAday = (ListView) rootView
				.findViewById(R.id.questions_for_day);
		return rootView;
	}
}
