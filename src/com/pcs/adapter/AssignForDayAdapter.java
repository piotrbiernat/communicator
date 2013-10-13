package com.pcs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.pcs.communicator.R;
import com.pcs.database.tables.Question;
import com.pcs.enums.Day;

public class AssignForDayAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private static List<Day> days = new ArrayList<Day>();
	private CheckDayAction checkDayAction;
	private Context ctx;

	public interface CheckDayAction {
		public void checkDay(Day day, boolean isChecked);

		public boolean containsQuestionForDay(Day day);
	}

	static {
		days.add(Day.MONDAY);
		days.add(Day.TUESDAY);
		days.add(Day.WEDNESDAY);
		days.add(Day.THURSDAY);
		days.add(Day.FRIDAY);
		days.add(Day.SATURDAY);
		days.add(Day.SUNDAY);
	}

	public class CheckOnClickListener implements OnClickListener {

		private Day day;

		public CheckOnClickListener(Day day) {
			this.day = day;
		}

		@Override
		public void onClick(View v) {
			checkDayAction.checkDay(day, ((CheckBox) v).isChecked());
		}
	}

	public AssignForDayAdapter(Context ctx, CheckDayAction checkDayAction,
			Question question) {
		this.ctx = ctx;
		this.checkDayAction = checkDayAction;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				parent, false);
		CheckBox checkBox = (CheckBox) rowView
				.findViewById(R.id.assignForDayCheckbox);
		checkBox.setText(ctx.getResources().getString(
				days.get(position).getResourceID()));
		checkBox.setChecked(checkDayAction.containsQuestionForDay(days.get(position)));
		checkBox.setOnClickListener(new CheckOnClickListener(days.get(position)));
		return rowView;
	}
}
