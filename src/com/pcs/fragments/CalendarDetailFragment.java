package com.pcs.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pcs.actions.CalendarDetailActions;
import com.pcs.adapter.QuestionWithAnswersAdapter;
import com.pcs.adapter.QuestionWithAnswersAdapter.ViewGroupHolder;
import com.pcs.communicator.CalendarQuestionActivity;
import com.pcs.communicator.R;
import com.pcs.database.query.AnswersQuery;
import com.pcs.database.query.QuestionForDayQuery;
import com.pcs.database.query.QuestionQuery;
import com.pcs.database.tables.Question;
import com.pcs.database.tables.QuestionForDay;
import com.pcs.enums.Day;
import com.pcs.views.DragAndDropExpandableListView;
import com.pcs.views.DragAndDropExpandableListView.DragDropHandlerListener;
import com.pcs.wrappers.QuestionForDayWrapper;

public class CalendarDetailFragment extends Fragment implements
		CalendarDetailActions {

	private Day day;
	private QuestionQuery questionQuery;
	private AnswersQuery answerQuery;
	private QuestionForDayQuery questionForDayQuery;
	private DragAndDropExpandableListView questionsListWithAnswers;

	private FrameLayout deleteZone;
	private RelativeLayout relativeLayout1;
	private RelativeLayout relativeLayout2;
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
				updateVisibility();
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
			dialog.show(getActivity().getSupportFragmentManager(),
					"QuestionListDialog");
		}
	}
	
	private class BeginClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent detailIntent = new Intent(getActivity(),
					CalendarQuestionActivity.class);
			detailIntent.putExtra(CalendarQuestionActivity.DAY_STRING, day);
			startActivity(detailIntent);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		day = (Day) getArguments().getSerializable(
				CalendarQuestionActivity.DAY_STRING);
		questionQuery = new QuestionQuery(getActivity());
		answerQuery = new AnswersQuery(getActivity());
		questionForDayQuery = new QuestionForDayQuery(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_calendar_detail,
				container, false);
		updateTitle();
		adapter = new QuestionWithAnswersAdapter(getActivity(), answerQuery,
				day, questionQuery.findQuestionForDayWrapperByDay(day));
		adapter.setCalendarDetailActionsHandler(this);
		questionsListWithAnswers = (DragAndDropExpandableListView) rootView.findViewById(R.id.question_list_with_answers);
		questionsListWithAnswers.setHeaderTitle(getHeaderTitle());
		questionsListWithAnswers.setAdapter(adapter);
		questionsListWithAnswers.setDragDropHandlerListener(createDragAndDropListener());
		if (getActivity().findViewById(R.id.delete_zone) != null) {
			deleteZone = (FrameLayout) getActivity().findViewById(
					R.id.delete_zone);
			deleteZone.setOnDragListener(new DragDeleteZone());
			animHide = AnimationUtils.loadAnimation(getActivity(),
					R.anim.hide_popup);
		}
		Button beginButton = (Button) rootView.findViewById(R.id.begin_button);
		beginButton.setOnClickListener(new BeginClickListener());

		Button addQuestion = (Button) rootView.findViewById(R.id.addQuestionToDay);
		addQuestion.setOnClickListener(new AddQuestionListener());
		
		Button addQuestionWhenNoQuestions = (Button) rootView.findViewById(R.id.addQuestionToDayWhenNoQuestions);
		addQuestionWhenNoQuestions.setOnClickListener(new AddQuestionListener());
		
		relativeLayout1 =  (RelativeLayout) rootView.findViewById(R.id.relativeLayout1);
		relativeLayout2 =  (RelativeLayout) rootView.findViewById(R.id.relativeLayout2);
		updateVisibility();
		return rootView;
	}

	private String getHeaderTitle() {
		String headerTitle = getActivity().getString(R.string.title_calendar_detail);
		headerTitle += " " + getActivity().getString(day.getResourceID());
		return headerTitle;
	}

	@Override
	public void removeQuestionFromDay(QuestionForDay questionForDay) {
		ConfirmationDialog confirmationDialog = new ConfirmationDialog();
		confirmationDialog.setConfirmationActions(this);
		Question question = questionQuery.get(questionForDay.getQuestionId());
		confirmationDialog.setQuestionText(question.getText());
		confirmationDialog.setOnPositivArgument(questionForDay);
		confirmationDialog.show(getActivity().getFragmentManager(), "removeQuestionFromDay");

	}

	@Override
	public void onPositiveConfirmation(Object onPositivArgument) {
		if (onPositivArgument instanceof QuestionForDay) {
			QuestionForDay questionForDay = (QuestionForDay) onPositivArgument;
			questionForDayQuery.delete(questionForDay.getId());
			updateAdapter();
		}
	}

	@Override
	public void editQuestion(QuestionForDayWrapper questionForDayWrapper,
			String newQuestionContent) {
		Question newQuestion = new Question();
		newQuestion.setText(newQuestionContent);
		List<Question> questions = questionQuery.listByExample(newQuestion);

		if (questions.isEmpty()) {
			QuestionForDay questionForDay2Update = questionForDayWrapper;
			questionForDayWrapper.setQuestionId(questionQuery.insert(newQuestion));
			questionForDayQuery.update(questionForDay2Update);
		} else {
			Question existingQuestion = questions.get(0);
			QuestionForDay questionForDay2Update = questionForDayWrapper;
			questionForDay2Update.setQuestionId(existingQuestion.getId());
			questionForDayQuery.update(questionForDay2Update);
		}
		updateAdapter();
	}

	@Override
	public void addQuestions(Day day, List<Question> questions) {

		for (Question question : questions) {
			questionForDayQuery.saveQuestionForDay(question, day);
		}
		updateAdapter();
		updateVisibility();
	}

	@Override
	public void handelDrag(View selectedItem, View dragItem) {
		if (selectedItem.getTag() instanceof ViewGroupHolder
				&& dragItem.getTag() instanceof ViewGroupHolder) {
			QuestionForDay selectedQuestion = getQuestionFromTag(selectedItem);
			QuestionForDay dragQuestion = getQuestionFromTag(dragItem);
			int selectedQuestionOrder = selectedQuestion.getQuestionOrder();
			int dragQuestionOrder = dragQuestion.getQuestionOrder();
			selectedQuestion.setQuestionOrder(dragQuestionOrder);
			questionForDayQuery.update(selectedQuestion);
			updateOrderOfQuestions(selectedQuestionOrder, dragQuestionOrder);
		}
	}

	private void updateOrderOfQuestions(int selectedQuestionOrder,
			int dragQuestionOrder) {
		if (selectedQuestionOrder != dragQuestionOrder) {
			if (selectedQuestionOrder > dragQuestionOrder) {
				for (int i = dragQuestionOrder - 1; i < selectedQuestionOrder - 1; i++) {
					QuestionForDayWrapper q = adapter.getGroup(i);
					q.setQuestionOrder(q.getQuestionOrder() + 1);
					questionForDayQuery.update(q);
				}
			} else {
				for (int i = selectedQuestionOrder; i < dragQuestionOrder; i++) {
					QuestionForDayWrapper q = adapter.getGroup(i);
					q.setQuestionOrder(q.getQuestionOrder() - 1);
					questionForDayQuery.update(q);
				}
			}
			updateAdapter();
		}
		updateVisibility();
	}

	private QuestionForDay getQuestionFromTag(View v) {
		if (v.getTag() instanceof ViewGroupHolder) {
			ViewGroupHolder holderSelectedItem = (ViewGroupHolder) v.getTag();
			return holderSelectedItem.getQuestionForDay();
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

	private void updateTitle() {
		String title = getActivity().getResources().getString(R.string.calender);
		title += " - " + getActivity().getResources().getString(day.getResourceID());
		getActivity().getActionBar().setTitle(title);
	}

	private void updateVisibility() {
		if (adapter.getGroupCount() > 0) {
			relativeLayout1.setVisibility(View.VISIBLE);
			questionsListWithAnswers.setVisibility(View.VISIBLE);
			relativeLayout2.setVisibility(View.GONE);
		} else {
			relativeLayout2.setVisibility(View.VISIBLE);
			relativeLayout1.setVisibility(View.GONE);
			questionsListWithAnswers.setVisibility(View.GONE);
		}
	}

	private void updateAdapter() {
		adapter.setQuestionsForDayWrapper(questionQuery.findQuestionForDayWrapperByDay(day));
	}

}
