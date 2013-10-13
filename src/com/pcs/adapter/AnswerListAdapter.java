package com.pcs.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.pcs.communicator.R;
import com.pcs.database.query.AnswersQuery;
import com.pcs.database.tables.Answer;
import com.pcs.database.tables.ImageContent;
import com.pcs.database.tables.dao.ImageContentDao;
import com.pcs.enums.Day;
import com.pcs.fragments.GalleryDialogFragment;
import com.pcs.fragments.GalleryDialogFragment.GalleryDialogSelection;
import com.pcs.utils.AssetImageManager;

public class AnswerListAdapter extends BaseAdapter implements
		GalleryDialogSelection {

	private List<Answer> answers;
	private LayoutInflater inflater;
	private AssetImageManager assetManager;
	private long questionId;
	private Day day;
	private FragmentActivity context;
	private AnswersQuery answerQuery;


	public class Tag {
		public ImageView image;
		public ImageView isCorrect;
	}

	public AnswerListAdapter(List<Answer> answers, long questionId, Day day,
			FragmentActivity context, AnswersQuery answerQuery) {
		this.questionId = questionId;
		this.day = day;
		this.context = context;
		this.answerQuery = answerQuery;
		this.setAnswers(answers);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		assetManager = new AssetImageManager(context);
	}

	private class AddAnswer implements OnClickListener {

		@Override
		public void onClick(View v) {
			GalleryDialogFragment newFragment = new GalleryDialogFragment();
			newFragment.setGalleryDialogSelection(AnswerListAdapter.this);
			newFragment.setImageContentDao(new ImageContentDao(context));
			newFragment.show(context.getSupportFragmentManager(),
					"GalleryDialogFragment");
		}
	}

	@Override
	public int getCount() {
		return getAnswers().size() + 1;
	}

	@Override
	public Answer getItem(int position) {
		return getAnswers().get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position - 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		Tag tag;
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.answer_row_layout, parent, false);
			tag = new Tag();
			tag.image = (ImageView) rowView.findViewById(R.id.answer_image);
			tag.isCorrect = (ImageView) rowView.findViewById(R.id.is_correct);
			rowView.setTag(tag);
		}
		tag = (Tag) rowView.getTag();
		ImageView answerImage = tag.image;
		if (position == 0) {
			answerImage.setImageResource(R.drawable.add);
			answerImage.setOnClickListener(new AddAnswer());
		} else if (position > 0) {

			Answer answer = getItem(position);
			try {
				answerImage.setImageDrawable(assetManager.getResizeImage((answer.getLink())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (answer.isCorrect()) {
				tag.isCorrect.setVisibility(View.VISIBLE);
			} else {
				tag.isCorrect.setVisibility(View.INVISIBLE);
			}
		}
		return rowView;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void addAnswers(List<Answer> answers) {
		this.answers.addAll(answers);
		notifyDataSetChanged();
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
		notifyDataSetChanged();
	}

	@Override
	public void selectedImages(List<ImageContent> imagesContent) {
		for (ImageContent imageContent : imagesContent) {
			Answer createAnswer = createAnswer(imageContent.getImageLink());
			answerQuery.insert(createAnswer);
			this.answers.add(createAnswer);
		}
		notifyDataSetChanged();
	}

	private Answer createAnswer(String imageLink) {
		Answer resultAnswer = new Answer();
		resultAnswer.setLink(imageLink);
		resultAnswer.setForDay(day);
		resultAnswer.setQuestionForDayID(questionId);
		return resultAnswer;
	}

}
