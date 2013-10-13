package com.pcs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.communicator.R;
import com.pcs.database.tables.Question;
import com.pcs.fragments.QuestionManagerListFragment.QuestionManagerActions;

public class QuestionListAdapter extends BaseAdapter {
	private List<Question> questions = new ArrayList<Question>();
	private Context context;
	private QuestionManagerActions managerAction;
	private LayoutInflater inflater;

	public QuestionListAdapter(List<Question> questions, Context context) {
		this.context = context;
		if (context instanceof QuestionManagerActions) {
			managerAction = (QuestionManagerActions) context;
		}
		this.setQuestions(questions);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public QuestionListAdapter(List<Question> questions, Context context,
			QuestionManagerActions questionManagerActions) {
		this.context = context;
		managerAction = questionManagerActions;

		this.setQuestions(questions);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private class RemoveQuestionListener implements OnClickListener {

		private Question question;

		public RemoveQuestionListener(Question question) {
			this.question = question;
		}

		@Override
		public void onClick(View v) {
			if (managerAction != null) {
				managerAction.deleteQuestion(question.getId());
			}
		}
	}

	@Override
	public int getCount() {
		return getQuestions().size() + 1;
	}

	@Override
	public Question getItem(int position) {
		return getQuestions().get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (position == 0) {
			View rowView = inflater.inflate(R.layout.question_row_layout,
					parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			textView.setText(R.string.addExisting);
			ImageView addButton = (ImageView) rowView
					.findViewById(R.id.deleteAddButton);
			addButton.setImageResource(R.drawable.add);
			addButton.setClickable(false);
			return rowView;
		} else {
			View rowView = inflater.inflate(R.layout.question_row_layout,
					parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			ImageView deleteButton = (ImageView) rowView
					.findViewById(R.id.deleteAddButton);
			deleteButton.setImageResource(R.drawable.bin_small);
			deleteButton.setOnClickListener(new RemoveQuestionListener(
					(Question) getItem(position)));
			Question question = getItem(position);
			textView.setText(question.getText());
			return rowView;
		}

	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
		notifyDataSetChanged();
	}
}
