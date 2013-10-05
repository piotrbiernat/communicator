package com.pcs.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.pcs.adapter.CalendarListAdapter;
import com.pcs.communicator.CalendarMaintenanceActivity;
import com.pcs.communicator.R;
import com.pcs.enums.Day;

/**
 * A list fragment representing a list of Days. Activities containing this
 * fragment MUST implement the {@link Callbacks} interface.
 */
public class CalendarListFragment extends ListFragment {

	ArrayList<String> list;
	private Callbacks mCallbacks = sDummyCallbacks;
	private View lastSelectedListItem;

	public interface Callbacks {

		public void onItemSelected(Day day);
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(Day day) {
		}
	};

	public CalendarListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new CalendarListAdapter(
				(CalendarMaintenanceActivity) getActivity()));
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getListView().setSelector(R.drawable.selecte_state_background);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		listView.setItemChecked(position, true);
		mCallbacks.onItemSelected((Day) getListAdapter().getItem(position));
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	public void setDay(Day day) {
		for (int i = 0; i < getListAdapter().getCount(); i++) {
			Day currentDay = (Day) getListAdapter().getItem(i);
			if (currentDay == day) {
				getListView().setItemChecked(i, true);
				return;
			}
		}
	}
}
