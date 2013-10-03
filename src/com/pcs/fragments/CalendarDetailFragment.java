package com.pcs.fragments;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.actions.CalendarDetailActions;
import com.pcs.adapter.QuestionWithAnswersAdapter;
import com.pcs.adapter.QuestionWithAnswersAdapter.ViewGroupHolder;
import com.pcs.communicator.CalendarDetailActivity;
import com.pcs.communicator.CalendarQuestionActivity;
import com.pcs.communicator.R;
import com.pcs.database.query.AnswersQuery;
import com.pcs.database.query.QuestionQuery;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.wrappers.QuestionWrapper;
import com.pcs.enums.Day;
import com.pcs.views.DragAndDropExpandableListView;
import com.pcs.views.DragAndDropExpandableListView.DragDropHandlerListener;

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
	private DragAndDropExpandableListView questionsListWithAnswers;

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
				adapter.update();
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

	private class AddQuestionListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			QuestionListDialog dialog = new QuestionListDialog();
			dialog.setDay(day);
			dialog.setCalendarDetailActions(CalendarDetailFragment.this);
			dialog.show(getActivity().getSupportFragmentManager(), "QuestionListDialog");
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
		questionsListWithAnswers = (DragAndDropExpandableListView) rootView.findViewById(R.id.question_list_with_answers);
		questionsListWithAnswers.setAdapter(adapter);
		questionsListWithAnswers
				.setDragDropHandlerListener(createDragAndDropListener());
		if (getActivity().findViewById(R.id.delete_zone) != null) {
			deleteZone = (FrameLayout) getActivity().findViewById(
					R.id.delete_zone);
			deleteZone.setOnDragListener(new DragDeleteZone());
			animHide = AnimationUtils.loadAnimation(getActivity(),
					R.anim.hide_popup);
		}
		ImageView addQuestion = (ImageView) rootView.findViewById(R.id.addQuestionToDay);
		addQuestion.setOnClickListener(new AddQuestionListener());
		return rootView;
	}

	@Override
	public void removeQuestionFromDay(Day day, Question question) {
		ConfirmationDialog confirmationDialog = new ConfirmationDialog();
		confirmationDialog.setConfirmationActions(this);
		confirmationDialog.setQuestionText(question.getText());
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
			QuestionWrapper newQuestionWrapper = new QuestionWrapper(newQuestion);
			QuestionWrapper oldQuestionWrapper = new QuestionWrapper(question);
			oldQuestionWrapper.removeDay(day);
			newQuestionWrapper.setAvailableDays(Collections.singleton(day));

			int order = oldQuestionWrapper.getQuestion().getOrderForDay(day);
			newQuestionWrapper.getQuestion().setOrderForDay(order, day);

			questionQuery.update(oldQuestionWrapper.getQuestion());

			newQuestion.setId(questionQuery.insert(newQuestionWrapper.getQuestion()));
			answerQuery.reassignAnswersFromOldQuestion2NewQuestion(
					oldQuestionWrapper.getQuestion(),
					newQuestionWrapper.getQuestion());
			adapter.update();
		}
	}

	@Override
	public void addQuestions(Day day, List<Question> questions) {

		for (Question question : questions) {
			QuestionWrapper questionWrapper = new QuestionWrapper(question);
			Set<Day> availabeDays = questionWrapper.getAvailableDays();
			availabeDays.add(day);
			questionWrapper.setAvailableDays(availabeDays);
			questionQuery.update(questionWrapper.getQuestion());
		}
		adapter.update();
	}

	@Override
	public void handelDrag(View selectedItem, View dragItem) {
		if (selectedItem.getTag() instanceof ViewGroupHolder
				&& dragItem.getTag() instanceof ViewGroupHolder) {
			Question selectedQuestion = getQuestionFromTag(selectedItem);
			Question dragQuestion = getQuestionFromTag(dragItem);
			int selectedQuestionOrder = selectedQuestion.getOrderForDay(day);
			int dragQuestionOrder = dragQuestion.getOrderForDay(day);
			selectedQuestion.setOrderForDay(dragQuestionOrder, day);
			questionQuery.update(selectedQuestion);
			updateOrderOfQuestions(selectedQuestionOrder, dragQuestionOrder);
		}
	}

	private void updateOrderOfQuestions(int selectedQuestionOrder,
			int dragQuestionOrder) {
		if (selectedQuestionOrder != dragQuestionOrder) {
			if (selectedQuestionOrder > dragQuestionOrder) {
				for (int i = dragQuestionOrder - 1; i < selectedQuestionOrder - 1; i++) {
					Question q = (Question) adapter.getGroup(i);
					if (q.getOrderForDay(day) == i + 1) {
						q.setOrderForDay(q.getOrderForDay(day) + 1, day);
						questionQuery.update(q);
					} else {
						throw new IllegalArgumentException(
								"Operacja sie nie udala Question Order = "
										+ q.getOrderForDay(day) + " a i = " + i
										+ 1);
					}
				}
			} else {
				for (int i = selectedQuestionOrder; i < dragQuestionOrder; i++) {
					Question q = (Question) adapter.getGroup(i);
					if (q.getOrderForDay(day) == i + 1) {
						q.setOrderForDay(q.getOrderForDay(day) - 1, day);
						questionQuery.update(q);
					} else {
						throw new IllegalArgumentException(
								"Operacja sie nie udala Question Order = "
										+ q.getOrderForDay(day) + " a i = " + i
										+ 1);
					}
				}
			}
		}
		adapter.update();
	}

	private Question getQuestionFromTag(View v) {
		if (v.getTag() instanceof ViewGroupHolder) {
			ViewGroupHolder holderSelectedItem = (ViewGroupHolder) v.getTag();
			return holderSelectedItem.getQuestion();
		}
		return null;
	}

	private DragDropHandlerListener createDragAndDropListener() {
		return new DragDropHandlerListener() {
			@Override
			public void handelDropAction(View selectedView, View dropAreaView) {
				handelDrag(selectedView, dropAreaView);
			}
		};
	}
}
