package com.pcs.actions;

import java.util.List;

import android.view.View;

import com.pcs.database.tables.Question;
import com.pcs.enums.Day;
import com.pcs.fragments.ConfirmationDialog.ConfirmationActions;

public interface CalendarDetailActions extends ConfirmationActions {
	public void removeQuestionFromDay(Day day, Question question);

	public void editQuestion(Day day, Question qustion, String newQuestionContent);

	public void addQuestions(Day day, List<Question> questions);

	public void handelDrag(View selectedItem, View dragItem);
}
