package com.pcs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.communicator.R;
import com.pcs.database.tables.Question;

public class QuestionListAdapter extends BaseAdapter {
	private List<Question> questions = new ArrayList<Question>();
	private Context context;

	public QuestionListAdapter(List<Question> questions, Context context) {
		this.context = context;
		this.questions = questions;
	}

	@Override
	public int getCount() {
		return questions.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return questions.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			View rowView = inflater.inflate(R.layout.question_row_layout,
					parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			ImageView deleteButton = (ImageView) rowView
					.findViewById(R.id.deleteAddButton);
			deleteButton.setImageResource(android.R.drawable.ic_menu_delete);
			Question question = questions.get(position - 1);
			textView.setText(question.getText());
			return rowView;
		}

	}
}
