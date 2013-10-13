package com.pcs.adapter;

import java.util.Collections;
import java.util.List;

import android.content.ClipData;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.actions.CalendarDetailActions;
import com.pcs.communicator.R;
import com.pcs.database.query.AnswersQuery;
import com.pcs.database.tables.Answer;
import com.pcs.database.tables.QuestionForDay;
import com.pcs.enums.Day;
import com.pcs.fragments.EditQuestionDialog;
import com.pcs.views.HorizontalListView;
import com.pcs.views.HorizontalListView.OnItemClick;
import com.pcs.views.HorizontalListView.OnItemLongClick;
import com.pcs.views.HorizontalListView.OnItemTouch;
import com.pcs.wrappers.QuestionForDayWrapper;

public class QuestionWithAnswersAdapter extends BaseExpandableListAdapter {

	private List<QuestionForDayWrapper> questionsForDayWrapper;
	private Day day;
	private LayoutInflater inflater;
	private FragmentActivity ctx;
	private FrameLayout deletZone;
	private AnswersQuery answerQuery;
	private Animation animShow;
	private CalendarDetailActions calendarDetailActionsHandler;

	public class ViewGroupHolder {
		public ImageView getDelete() {
			return delete;
		}

		public void setDelete(ImageView delete) {
			this.delete = delete;
		}

		public ImageView getEdit() {
			return edit;
		}

		public void setEdit(ImageView edit) {
			this.edit = edit;
		}

		public TextView getLabel() {
			return label;
		}

		public void setLabel(TextView label) {
			this.label = label;
		}

		public QuestionForDayWrapper getQuestionForDay() {
			return questionForDay;
		}

		public void setQuestionForDay(QuestionForDayWrapper questionForDay) {
			this.questionForDay = questionForDay;
		}

		ImageView delete;
		ImageView edit;
		TextView label;
		QuestionForDayWrapper questionForDay;
	}

	private class ViewChildHolder {
		HorizontalListView hlv;
	}

	public QuestionWithAnswersAdapter(FragmentActivity ctx,
			AnswersQuery answerQuery, Day day, List<QuestionForDayWrapper> questionsForDayWrapper) {
		this.ctx = ctx;
		this.answerQuery = answerQuery;
		this.day = day;
		this.questionsForDayWrapper = questionsForDayWrapper;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deletZone = (FrameLayout) ctx.findViewById(R.id.delete_zone);
		animShow = AnimationUtils.loadAnimation(ctx, R.anim.show_popup);
		setQuestionsForDayWrapper(questionsForDayWrapper);
		if (ctx instanceof CalendarDetailActions) {
			setCalendarDetailActionsHandler((CalendarDetailActions) ctx);
		}
	}

	private class OnClickDeleteQuestion implements OnClickListener {

		private int index;

		public OnClickDeleteQuestion(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			QuestionForDay questionForDay = getGroup(index);
			getCalendarDetailActionsHandler().removeQuestionFromDay(
					questionForDay);
		}
	}

	private class OnClickEditQuestion implements OnClickListener {

		private int index;

		public OnClickEditQuestion(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			QuestionForDayWrapper questionForDayWrapper = getGroup(index);
			EditQuestionDialog editQuestionDialog = new EditQuestionDialog();
			editQuestionDialog.setQuestionForDayWrapper(questionForDayWrapper);
			editQuestionDialog.setCalendarDetailActions(getCalendarDetailActionsHandler());
			editQuestionDialog.show(ctx.getFragmentManager(),"EditQuestionDialog");
			getCalendarDetailActionsHandler();
		}
	}

	private class OnAnswerClick implements OnItemClick {

		private AnswerListAdapter answerListAdapter;

		public OnAnswerClick(AnswerListAdapter answerListAdapter) {
			this.answerListAdapter = answerListAdapter;
		}

		@Override
		public void itemClick(View view, int position) {
			if (position > 0) {
				ImageView isCorrect = (ImageView) view
						.findViewById(R.id.is_correct);
				Answer answer = answerListAdapter.getItem(position);
				if (answer.isCorrect()) {
					isCorrect.setVisibility(View.INVISIBLE);
				} else {
					isCorrect.setVisibility(View.VISIBLE);
				}
				answer.setCorrect(!answer.isCorrect());
				answerQuery.update(answer);
			}
		}
	}

	private class OnAnswerLongClick implements OnItemLongClick {
		@Override
		public boolean itemLongClick(View v, int position, Object item) {
			if (item instanceof Answer) {
				Answer answer = (Answer) item;
				deletZone.startAnimation(animShow);
				deletZone.setVisibility(View.VISIBLE);
				ClipData data = ClipData.newPlainText("",
						Long.toString(answer.getId()));
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
				v.startDrag(data, shadowBuilder, v, 0);
				v.setVisibility(View.INVISIBLE);
				return true;
			}
			return false;
		}
	}

	private class OnAnswerTouch implements OnItemTouch {

		@Override
		public boolean itemTouch(View view, MotionEvent event, int position) {
			return false;
		}

	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public int getGroupCount() {
		return questionsForDayWrapper.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public QuestionForDayWrapper getGroup(int groupPosition) {
		return questionsForDayWrapper.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		QuestionForDayWrapper questionForDayWrapper = getGroup(groupPosition);

		Answer exampleObj = new Answer();
		exampleObj.setQuestionForDayID(questionForDayWrapper.getId());
		exampleObj.setForDay(day);
		List<Answer> answers = answerQuery.listByExample(exampleObj);
		return answers;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.question_answer_row_layout,
					parent, false);
			ViewGroupHolder holder = new ViewGroupHolder();

			holder.delete = (ImageView) view.findViewById(R.id.deleteAddButton);
			holder.edit = (ImageView) view.findViewById(R.id.editQuestion);
			holder.label = (TextView) view.findViewById(R.id.label);
			view.setTag(holder);
		}
		QuestionForDayWrapper questionForDayWrapper = getGroup(groupPosition);
		ViewGroupHolder holder = (ViewGroupHolder) view.getTag();
		holder.delete.setOnClickListener(new OnClickDeleteQuestion(groupPosition));
		holder.edit.setOnClickListener(new OnClickEditQuestion(groupPosition));
		holder.label.setText(questionForDayWrapper.getQuestion().getText());
		holder.questionForDay = questionForDayWrapper;
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.answers_row_layout, parent, false);
			ViewChildHolder holder = new ViewChildHolder();
			holder.hlv = (HorizontalListView) view
					.findViewById(R.id.horizontalScrollView1);
			view.setTag(holder);
		}
		QuestionForDayWrapper questionForDayWrapper = getGroup(groupPosition);

		ViewChildHolder holder = (ViewChildHolder) view.getTag();
		@SuppressWarnings("unchecked")
		List<Answer> answers = (List<Answer>) getChild(groupPosition, childPosition);
		AnswerListAdapter adapter = new AnswerListAdapter(answers,
				questionForDayWrapper.getId(), day, ctx, answerQuery);
		holder.hlv.setAdapter(adapter);
		holder.hlv.setOnItemClick(new OnAnswerClick(adapter));
		holder.hlv.setOnItemLongClick(new OnAnswerLongClick());
		holder.hlv.setOnItemTouch(new OnAnswerTouch());
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public CalendarDetailActions getCalendarDetailActionsHandler() {
		return calendarDetailActionsHandler;
	}

	public void setCalendarDetailActionsHandler(
			CalendarDetailActions calendarDetailActionsHandler) {
		this.calendarDetailActionsHandler = calendarDetailActionsHandler;
	}

	public List<QuestionForDayWrapper> getQuestionsForDayWrapper() {
		return questionsForDayWrapper;
	}

	public void setQuestionsForDayWrapper(List<QuestionForDayWrapper> questionsForDayWrapper) {
		this.questionsForDayWrapper = questionsForDayWrapper;
		Collections.sort(this.questionsForDayWrapper);
		notifyDataSetChanged();
	}

}
