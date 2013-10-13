package com.pcs.actions;

import java.util.List;

import android.view.View;

import com.pcs.database.tables.Question;
import com.pcs.database.tables.QuestionForDay;
import com.pcs.enums.Day;
import com.pcs.fragments.ConfirmationDialog.ConfirmationActions;
import com.pcs.wrappers.QuestionForDayWrapper;

public interface CalendarDetailActions extends ConfirmationActions {
	public void removeQuestionFromDay(QuestionForDay questionForDay);

	public void editQuestion(QuestionForDayWrapper qustionDayWrapper, String newQuestionContent);

	public void addQuestions(Day day, List<Question> questions);

	public void handelDrag(View selectedItem, View dragItem);
}
