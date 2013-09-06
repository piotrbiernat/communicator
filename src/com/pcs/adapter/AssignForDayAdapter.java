package com.pcs.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.pcs.communicator.R;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.wrappers.QuestionWrapper;
import com.pcs.enums.Day;

public class AssignForDayAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private static List<Day> days = new ArrayList<Day>();
	private QuestionWrapper questionWrapper;
	private Context context;
	static {
		days.add(Day.MONDAY);
		days.add(Day.TUESDAY);
		days.add(Day.WEDNESDAY);
		days.add(Day.THURSDAY);
		days.add(Day.FRIDAY);
		days.add(Day.SATURDAY);
		days.add(Day.SUNDAY);
	}

	public static class CheckOnClickListener implements OnClickListener {

		private QuestionWrapper questionWrapper;
		private Day day;

		public CheckOnClickListener(QuestionWrapper questionWrapper, Day day) {
			this.questionWrapper = questionWrapper;
			this.day = day;

		}

		@Override
		public void onClick(View v) {
			if (((CheckBox) v).isChecked()) {
				Set<Day> availableDays = questionWrapper.getAvailableDays();
				availableDays.add(day);
				questionWrapper.setAvailableDays(availableDays);
			} else {
				Set<Day> availableDays = questionWrapper.getAvailableDays();
				availableDays.remove(day);
				questionWrapper.setAvailableDays(availableDays);
			}
		}

	}

	public AssignForDayAdapter(Context context, Question question) {
		this.context = context;
		questionWrapper = new QuestionWrapper(question);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return days.size();
	}

	@Override
	public Day getItem(int position) {
		return days.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position - 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = inflater.inflate(R.layout.assign_for_day_row_view,
				parent,
				false);
		CheckBox checkBox = (CheckBox) rowView
				.findViewById(R.id.assignForDayCheckbox);
		checkBox.setText(context.getResources().getString(
				days.get(position).getResourceID()));
		checkBox.setChecked(questionWrapper.getAvailableDays().contains(
				days.get(position)));
		checkBox.setOnClickListener(new CheckOnClickListener(questionWrapper,
				days.get(position)));
		return rowView;
	}
}
