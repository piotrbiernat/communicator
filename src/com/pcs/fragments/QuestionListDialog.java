package com.pcs.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.pcs.actions.CalendarDetailActions;
import com.pcs.adapter.QuestionListDialogAdpter;
import com.pcs.communicator.R;
import com.pcs.database.query.QuestionQuery;
import com.pcs.database.tables.Question;
import com.pcs.enums.Day;

public class QuestionListDialog extends DialogFragment {

	private CalendarDetailActions calendarDetailActions;
	private QuestionQuery questionQuery;
	private QuestionListDialogAdpter questionListDialogAdpter;
	private Day day;

	private class OnListItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			CheckBox selected = (CheckBox) view.findViewById(R.id.select);
			if (selected.isChecked()) {
				questionListDialogAdpter.deselectItem(position);
			} else {
				questionListDialogAdpter.selectItem(position);
			}
			selected.toggle();
		}

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		questionQuery = new QuestionQuery(getActivity());
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(createView());
		builder.setPositiveButton(R.string.yesString,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								calendarDetailActions.addQuestions(day, questionListDialogAdpter.getSelectedQuestions());
							}
						});
		return builder.create();
	}

	public CalendarDetailActions getCalendarDetailActions() {
		return calendarDetailActions;
	}

	public void setCalendarDetailActions(CalendarDetailActions calendarDetailActions) {
		this.calendarDetailActions = calendarDetailActions;
	}


	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	private View createView() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		ListView questionList = (ListView) inflater.inflate(R.layout.questions_list_dialog, null);
		questionListDialogAdpter = createAdapter();
		questionList.setAdapter(questionListDialogAdpter);
		questionList.setOnItemClickListener(new OnListItemClick());
		return questionList;
	}
	
	private QuestionListDialogAdpter createAdapter() {
		if (getDay() == null) {
			throw new IllegalArgumentException("Day is not set");
		}
		List<Question> question = questionQuery.findQuestionWithoutDay(getDay());
		QuestionListDialogAdpter adapter = new QuestionListDialogAdpter(getActivity(), question);
		return adapter;
	}
}
