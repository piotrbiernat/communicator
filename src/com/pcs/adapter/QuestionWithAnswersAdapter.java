package com.pcs.adapter;

import java.util.List;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
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
import com.pcs.database.query.QuestionQuery;
import com.pcs.database.tables.Answer;
import com.pcs.database.tables.Question;
import com.pcs.enums.Day;
import com.pcs.fragments.EditQuestionDialog;
import com.pcs.views.HorizontalListView;
import com.pcs.views.HorizontalListView.OnItemClick;
import com.pcs.views.HorizontalListView.OnItemLongClick;
import com.pcs.views.HorizontalListView.OnItemTouch;

public class QuestionWithAnswersAdapter extends BaseExpandableListAdapter {

	private SparseArray<List<Answer>> answersAssignToQuestionId;
	private List<Question> questions;
	private Day day;
	private LayoutInflater inflater;
	private FragmentActivity ctx;
	private FrameLayout deletZone;
	private AnswersQuery answerQuery;
	private Animation animShow;
	private QuestionQuery questionQuery;
	private CalendarDetailActions calendarDetailActionsHandler;

	private class ViewGroupHolder {
		ImageView delete;
		ImageView edit;
		TextView label;
	}

	private class ViewChildHolder {
		HorizontalListView hlv;
	}

	public QuestionWithAnswersAdapter(FragmentActivity ctx,
			QuestionQuery questionQuery,
			AnswersQuery answerQuery, Day day) {
		this.ctx = ctx;
		this.questionQuery = questionQuery;
		this.answerQuery = answerQuery;
		this.day = day;
		questions = questionQuery.findQuestionForDay(day);
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deletZone = (FrameLayout) ctx.findViewById(R.id.delete_zone);
		animShow = AnimationUtils.loadAnimation(ctx, R.anim.show_popup);
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
			Question question = (Question) getGroup(index);
			getCalendarDetailActionsHandler().removeQuestionFromDay(day, question);
		}
	}

	private class OnClickEditQuestion implements OnClickListener {

		private int index;

		public OnClickEditQuestion(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			Question question = (Question) getGroup(index);
			EditQuestionDialog editQuestionDialog = new EditQuestionDialog();
			editQuestionDialog.setDay(day);
			editQuestionDialog.setQuestion(question);
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
			Answer answer = answerListAdapter.getItem(position);
			if (answer.isCorrect()) {
				view.setBackgroundDrawable(null);
			} else {
				view.setBackgroundColor(Color.BLUE);
			}
			answer.setCorrect(!answer.isCorrect());
			answerQuery.update(answer);
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
			}
			return true;
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
		return questions.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return questions.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		Question question = questions.get(groupPosition);
		return answersAssignToQuestionId.get((int) question.getId());
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
		Question question = questions.get(groupPosition);
		ViewGroupHolder holder = (ViewGroupHolder) view.getTag();
		holder.delete.setOnClickListener(new OnClickDeleteQuestion(groupPosition));
		holder.edit.setOnClickListener(new OnClickEditQuestion(groupPosition));
		holder.label.setText(question.getText());
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
		Question question = questions.get(groupPosition);

		Answer exampleObj = new Answer();
		exampleObj.setQuestionID(question.getId());

		ViewChildHolder holder = (ViewChildHolder) view.getTag();
		List<Answer> answers = answerQuery.listByExample(exampleObj);
		AnswerListAdapter adapter = new AnswerListAdapter(answers,
				question.getId(), day, ctx, answerQuery);
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
	
	public void update() {
		questions = questionQuery.findQuestionForDay(day);
		answersAssignToQuestionId = answerQuery.findAnswersForQuestionsAndDay(questions, day);
		notifyDataSetChanged();
	}

	public CalendarDetailActions getCalendarDetailActionsHandler() {
		return calendarDetailActionsHandler;
	}

	public void setCalendarDetailActionsHandler(
			CalendarDetailActions calendarDetailActionsHandler) {
		this.calendarDetailActionsHandler = calendarDetailActionsHandler;
	}

}
