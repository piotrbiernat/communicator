package com.pcs.fragments;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.actions.CalendarDetailActions;
import com.pcs.adapter.QuestionWithAnswersAdapter;
import com.pcs.communicator.CalendarDetailActivity;
import com.pcs.communicator.CalendarQuestionActivity;
import com.pcs.communicator.R;
import com.pcs.database.query.AnswersQuery;
import com.pcs.database.query.QuestionQuery;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.wrappers.QuestionWrapper;
import com.pcs.enums.Day;

/**
 * A fragment representing a single Day detail screen. This fragment is either
 * contained in a {@link DayListActivity} in two-pane mode (on tablets) or a
 * {@link CalendarDetailActivity} on handsets.
 */
public class CalendarDetailFragment extends Fragment implements
		CalendarDetailActions {

	private Day day;
	private QuestionQuery questionQuery;
	private AnswersQuery answerQuery;
	private ExpandableListView questionsListWithAnswers;

	private FrameLayout deleteZone;
	private Animation animHide;
	private QuestionWithAnswersAdapter adapter;

	private class DragDeleteZone implements OnDragListener {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			ImageView imageView = (ImageView) v.findViewById(R.id.delete_image);
			View selectedItem = (View) event.getLocalState();
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// Do nothing
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				if (imageView != null) {
					imageView.setImageResource(R.drawable.bin_deleted);
				}
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				if (imageView != null) {
					imageView.setImageResource(R.drawable.bin);
				}
				break;
			case DragEvent.ACTION_DROP:
				String answer2Delete = (String) event.getClipData()
						.getItemAt(0).getText();
				Long answerId = Long.parseLong(answer2Delete);
				answerQuery.delete(answerId);
				ViewGroup owner = (ViewGroup) selectedItem.getParent();
				owner.removeView(selectedItem);
				v.startAnimation(animHide);
				v.setVisibility(View.GONE);
				imageView.setImageResource(R.drawable.bin);
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				v.startAnimation(animHide);
				v.setVisibility(View.GONE);
				selectedItem.setVisibility(View.VISIBLE);
				imageView.setImageResource(R.drawable.bin);
			default:
				break;
			}
			return true;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		day = (Day) getArguments().getSerializable(
				CalendarQuestionActivity.DAY_STRING);
		questionQuery = new QuestionQuery(getActivity());
		answerQuery = new AnswersQuery(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_calendar_detail,
				container, false);
		((TextView) rootView.findViewById(R.id.calendar_detail_text))
				.setText(getActivity().getResources().getString(
						day.getResourceID()));
		adapter = new QuestionWithAnswersAdapter(getActivity(), questionQuery,
				answerQuery, day);
		adapter.setCalendarDetailActionsHandler(this);
		questionsListWithAnswers = (ExpandableListView) rootView.findViewById(R.id.question_list_with_answers);
		questionsListWithAnswers.setAdapter(adapter);
		if (getActivity().findViewById(R.id.delete_zone) != null) {
			deleteZone = (FrameLayout) getActivity().findViewById(
					R.id.delete_zone);
			deleteZone.setOnDragListener(new DragDeleteZone());
			animHide = AnimationUtils.loadAnimation(getActivity(),
					R.anim.hide_popup);
		}
		return rootView;
	}

	@Override
	public void removeQuestionFromDay(Day day, Question question) {
		ConfirmationDialog confirmationDialog = new ConfirmationDialog();
		confirmationDialog.setConfirmationActions(this);
		confirmationDialog.setOnPositivArgument(new Pair<Day, Question>(day, question));
		confirmationDialog.show(getActivity().getFragmentManager(), "removeQuestionFromDay");

	}

	@Override
	public void onPositiveConfirmation(Object onPositivArgument) {
		if (onPositivArgument instanceof Pair) {
			@SuppressWarnings("unchecked")
			Pair<Day, Question> pair = (Pair<Day, Question>) onPositivArgument;
			Day day2Remove = pair.first;
			Question question = pair.second;
			QuestionWrapper questionWrapper = new QuestionWrapper(question);
			questionWrapper.removeDay(day2Remove);
			questionQuery.update(questionWrapper.getQuestion());
			answerQuery.deleteAllAnswersAsociated2Question(question);
			adapter.update();
		}
	}

	@Override
	public void editQuestion(Day day, Question question, String newQuestionContent) {
		Question newQuestion = new Question(newQuestionContent);
		List<Question> foundQuestions = questionQuery
				.listByExample(newQuestion);
		if (foundQuestions.isEmpty()) {
			QuestionWrapper newQuestionWrapper = new QuestionWrapper(
					newQuestion);
			QuestionWrapper oldQuestionWrapper = new QuestionWrapper(question);
			oldQuestionWrapper.removeDay(day);
			newQuestionWrapper.setAvailableDays(Collections.singleton(day));
			questionQuery.update(oldQuestionWrapper.getQuestion());
			newQuestion.setId(questionQuery.insert(newQuestionWrapper
					.getQuestion()));
			answerQuery.reassignAnswersFromOldQuestion2NewQuestion(
					oldQuestionWrapper.getQuestion(),
					newQuestionWrapper.getQuestion());
			adapter.update();
		}
	}

}
