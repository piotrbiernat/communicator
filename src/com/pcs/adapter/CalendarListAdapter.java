package com.pcs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.communicator.CalendarDetailActivity;
import com.pcs.communicator.CalendarDetailFragment;
import com.pcs.communicator.CalendarListActivity;
import com.pcs.communicator.CalendarQuestionActivity;
import com.pcs.communicator.R;
import com.pcs.enums.Day;

public class CalendarListAdapter extends BaseAdapter {
	private final CalendarListActivity activity;
	private static List<Day> days = new ArrayList<Day>();
	static {
		days.add(Day.MONDAY);
		days.add(Day.TUESDAY);
		days.add(Day.WEDNESDAY);
		days.add(Day.THURSDAY);
		days.add(Day.FRIDAY);
		days.add(Day.SATURDAY);
		days.add(Day.SUNDAY);
	}

	public CalendarListAdapter(CalendarListActivity fragment) {
		this.activity = fragment;

	}

	private class TutorOnClickListener implements OnClickListener {

		private Day day;

		public TutorOnClickListener(Day day) {
			this.day = day;

		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(activity, CalendarQuestionActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(CalendarQuestionActivity.TUTOR_MODE, true);
			intent.putExtra(CalendarQuestionActivity.DAY_STRING, day);
			activity.startActivity(intent);
		}
	}

	private class EditOnClickListener implements OnClickListener {

		private Day day;

		public EditOnClickListener(Day day) {
			this.day = day;

		}

		@Override
		public void onClick(View v) {
			if (activity.isTwoPane()) {
				// In two-pane mode, show the detail view in this activity by
				// adding or replacing the detail fragment using a
				// fragment transaction.
				Bundle arguments = new Bundle();
				arguments.putSerializable(CalendarQuestionActivity.DAY_STRING,
						day);
				CalendarDetailFragment fragment = new CalendarDetailFragment();
				fragment.setArguments(arguments);
				activity.getSupportFragmentManager().beginTransaction()
						.replace(R.id.calendar_detail_container, fragment)
						.commit();

			} else {
				// In single-pane mode, simply start the detail activity
				// for the selected item ID.
				Intent detailIntent = new Intent(activity,
						CalendarDetailActivity.class);
				detailIntent.putExtra(CalendarQuestionActivity.DAY_STRING, day);
				activity.startActivity(detailIntent);
			}

		}

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.calendar_row_layout, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView editButton = (ImageView) rowView
				.findViewById(R.id.editButton);
		ImageView tutorButton = (ImageView) rowView
				.findViewById(R.id.tutorButton);

		Day day = days.get(position);

		textView.setText(activity.getResources().getString(day.getResourceID()));

		editButton.setOnClickListener(new EditOnClickListener(day));

		tutorButton.setOnClickListener(new TutorOnClickListener(day));

		return rowView;
	}

	@Override
	public int getCount() {
		return days.size();
	}

	@Override
	public Object getItem(int position) {
		return days.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}