package com.pcs.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pcs.actions.CalendarDetailActions;
import com.pcs.communicator.R;
import com.pcs.database.tables.Question;
import com.pcs.enums.Day;

public class EditQuestionDialog extends DialogFragment {

	private CalendarDetailActions calendarDetailActions;
	private Day day;
	private Question question;
	private TextView questionTextField;

	public interface ConfirmationActions {
		public void onPositiveConfirmation(Object onPositivArgument);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setPositiveButton(R.string.yesString,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
						getCalendarDetailActions().editQuestion(getDay(),
								getQuestion(), questionTextField.getText()
										.toString());
							}
						}).setNegativeButton(R.string.noString, null);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.edit_question, null);
		questionTextField = (TextView) view.findViewById(R.id.question_text);
		questionTextField.setText(question.getText());
		builder.setView(view);
		return builder.create();
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public CalendarDetailActions getCalendarDetailActions() {
		return calendarDetailActions;
	}

	public void setCalendarDetailActions(CalendarDetailActions calendarDetailActions) {
		this.calendarDetailActions = calendarDetailActions;
	}

}
