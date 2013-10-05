package com.pcs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pcs.communicator.R;
import com.pcs.enums.Day;

public class CalendarListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private static List<Day> days = new ArrayList<Day>();
	private Context ctx;
	static {
		days.add(Day.MONDAY);
		days.add(Day.TUESDAY);
		days.add(Day.WEDNESDAY);
		days.add(Day.THURSDAY);
		days.add(Day.FRIDAY);
		days.add(Day.SATURDAY);
		days.add(Day.SUNDAY);
	}

	private class Holder {
		TextView textView;
	}

	public CalendarListAdapter(Context ctx) {
		this.ctx = ctx;
		inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			rowView = inflater.inflate(android.R.layout.simple_list_item_1,
					parent, false);
			Holder h = new Holder();
			h.textView = (TextView) rowView
					.findViewById(android.R.id.text1);
			rowView.setTag(h);
			rowView.setBackgroundResource(R.drawable.selecte_state_background);
		}
		Holder h = (Holder) rowView.getTag();
		Day day = days.get(position);
		h.textView.setText(ctx.getResources().getString(day.getResourceID()));
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