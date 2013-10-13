package com.pcs.database.query;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.pcs.database.tables.Question;
import com.pcs.database.tables.QuestionForDay;
import com.pcs.database.tables.dao.QuestionDao;
import com.pcs.enums.Day;
import com.pcs.wrappers.QuestionForDayWrapper;

public class QuestionQuery extends QuestionDao {

	private QuestionForDayQuery questionForDayQuery;
	
	public QuestionQuery(Context ctx) {
		super(ctx);
		questionForDayQuery = new QuestionForDayQuery(ctx);
	}

	@Override
	public long insert(Question question) {
		return super.insert(question);
	}

	@Override
	public long update(Question question) {
		return super.update(question);
	}

	@Override
	public int delete(Long id) {
		for(QuestionForDay questionForDay: questionForDayQuery.findQuestionForDayByQuestionId(id)) {
			questionForDayQuery.delete(questionForDay.getId());
		}
		return super.delete(id);
	}
	
	public List<QuestionForDayWrapper> questionForDayWrapper(List<QuestionForDay> questionsForDay) {
		List<QuestionForDayWrapper> questionForDayWrappers = new ArrayList<QuestionForDayWrapper>();
		for (QuestionForDay questionForDay : questionsForDay) {
			QuestionForDayWrapper questionForDayWrapper = new QuestionForDayWrapper(questionForDay);
			questionForDayWrapper.setQuestion(get(questionForDayWrapper.getQuestionId()));
			questionForDayWrappers.add(questionForDayWrapper);
		}
		return questionForDayWrappers;
	}

	public List<QuestionForDayWrapper> findQuestionForDayWrapperByDay(Day day) {
		return questionForDayWrapper(questionForDayQuery.findQuestionForDayByDay(day));
	}

}
