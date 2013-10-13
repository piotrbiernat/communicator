package com.pcs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pcs.communicator.R;
import com.pcs.database.tables.Question;

public class QuestionListDialogAdpter extends BaseAdapter {

	private List<Question> question;
	private LayoutInflater inflater;
	private List<Question> selectedQuestions = new ArrayList<Question>();

	private class Holder {
		CheckBox selected;
		TextView questionText;
	}

	public QuestionListDialogAdpter(Context ctx, List<Question> question) {
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.question = question;
	}

	@Override
	public int getCount() {
		return question.size();
	}

	@Override
	public Question getItem(int position) {
		return question.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			Holder holder = new Holder();
			view = inflater.inflate(R.layout.question_list_dialog_item, parent, false);
			holder.questionText = (TextView) view.findViewById(R.id.questionText);
			holder.selected = (CheckBox) view.findViewById(R.id.select);
			view.setTag(holder);
		}
		Holder holder = (Holder) view.getTag();
		Question question = getItem(position);
		holder.questionText.setText(question.getText());
		return view;
	}

	public List<Question> getSelectedQuestions() {
		return selectedQuestions;
	}


	public void selectItem(int position) {
		Question selectedQuestion = getItem(position);
		getSelectedQuestions().add(selectedQuestion);
	}

	public void deselectItem(int position) {
		Question selectedQuestion = getItem(position);
		getSelectedQuestions().remove(selectedQuestion);
	}

}
