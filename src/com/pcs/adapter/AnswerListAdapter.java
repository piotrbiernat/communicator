package com.pcs.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.communicator.R;
import com.pcs.database.tables.Answer;

public class AnswerListAdapter extends BaseAdapter {

	private List<Answer> answers;
	private LayoutInflater inflater;

	public AnswerListAdapter(List<Answer> answers, Context context) {
		this.answers = answers;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return answers.size() + 1;
	}

	@Override
	public Answer getItem(int position) {
		return answers.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position - 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == 0) {
			View rowView = inflater.inflate(R.layout.question_row_layout,
					parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			textView.setText(R.string.addNew);
			ImageView addButton = (ImageView) rowView
					.findViewById(R.id.deleteAddButton);
			addButton.setImageResource(android.R.drawable.ic_menu_add);
			return rowView;
		} else {
			View rowView = inflater.inflate(R.layout.answer_row_layout,
					parent, false);
			ImageView answerImage = (ImageView) rowView
					.findViewById(R.id.answer_image);
			Answer question = getItem(position);

			return rowView;
		}
	}

}
