package com.pcs.adapter;

import java.io.IOException;
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
import com.pcs.utils.AssetImageManager;

public class AnswerListAdapter extends BaseAdapter {

	private List<Answer> answers;
	private LayoutInflater inflater;
	private AssetImageManager assetManager;

	public AnswerListAdapter(List<Answer> answers, Context context) {
		this.setAnswers(answers);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		assetManager = new AssetImageManager(context);
	}

	@Override
	public int getCount() {
		return getAnswers().size() + 1;
	}

	@Override
	public Answer getItem(int position) {
		return getAnswers().get(position - 1);
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
			Answer answer = getItem(position);
			try {
				answerImage.setImageDrawable(assetManager.getResizeImage(answer
						.getLink()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return rowView;
		}
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void addAnswers(List<Answer> answers) {
		this.answers.addAll(answers);
		notifyDataSetChanged();
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
		notifyDataSetChanged();
	}

}
