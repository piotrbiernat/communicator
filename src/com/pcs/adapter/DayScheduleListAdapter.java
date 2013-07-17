package com.pcs.adapter;

import java.util.List;

import com.pcs.communicator.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DayScheduleListAdapter extends BaseAdapter {

	private List<String> days;
	private LayoutInflater inflanter;
	
	public DayScheduleListAdapter(Context context, List<String> days) {
		this.days = days;
		inflanter = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflanter.inflate(R.layout.day_schedule_list_item, parent, false);
		TextView textView = (TextView) view.findViewById(R.id.day_schedule_item);
		textView.setText(days.get(position));
		return view;
	}

}
