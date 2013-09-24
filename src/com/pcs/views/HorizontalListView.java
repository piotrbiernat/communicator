package com.pcs.views;


import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.pcs.communicator.R;

public class HorizontalListView extends HorizontalScrollView {

	private DataSetObserver observer = new DataSetObserver() {
		public void onChanged() {
			updateChildren();
		};
	};

	public interface OnItemClick {
		public void itemClick(View view, int position);
	}

	public interface OnItemLongClick {
		public boolean itemLongClick(View view, int position, Object item);
	}

	public interface OnItemTouch {
		public boolean itemTouch(View view, MotionEvent event, int position);
	}

	private ListAdapter adapter;
	private OnItemClick onItemClick;
	private OnItemLongClick onItemLongClick;
	private OnItemTouch onItemTouch;
	private LinearLayout ll;


	private class ItemClickListener implements OnClickListener {

		private int index;

		public ItemClickListener(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			if (onItemClick != null) {
				onItemClick.itemClick(v, index);
			}
		}
	}

	private class ItemLongClickListener implements OnLongClickListener {

		private int index;

		public ItemLongClickListener(int index) {
			this.index = index;
		}

		@Override
		public boolean onLongClick(View v) {
			if (onItemLongClick != null) {
				Object item = getAdapter().getItem(index);
				return onItemLongClick.itemLongClick(v, index, item);
			}
			return false;
		}

	}

	private class ItemTouchListener implements OnTouchListener {

		private int index;

		public ItemTouchListener(int index) {
			this.index = index;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (onItemTouch != null) {
				return onItemTouch.itemTouch(v, event, index);
			}
			return false;
		}

	}

	public HorizontalListView(Context context) {
		super(context);
		init();
	}

	public HorizontalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HorizontalListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ListAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ListAdapter adapter) {
		if (this.adapter != null) {
			this.adapter.unregisterDataSetObserver(observer);
		}
		this.adapter = adapter;
		this.adapter.registerDataSetObserver(observer);
		updateChildren();
	}

	public void updateChildren() {
		int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			View childAt = ll.getChildAt(i);
			View child = adapter.getView(i, childAt, this);
			child.setOnClickListener(new ItemClickListener(i));
			child.setOnLongClickListener(new ItemLongClickListener(i));
			child.setOnTouchListener(new ItemTouchListener(i));
			if (childAt == null) {
				ll.addView(child, i);
			}
		}
		if (count < ll.getChildCount()) {
			int childCount = ll.getChildCount();
			ll.removeViews(count, childCount - count);
		}

		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean result = super.onTouchEvent(ev);

		return result;
	}

	public OnItemClick getOnItemClick() {
		return onItemClick;
	}

	public void setOnItemClick(OnItemClick onItemClick) {
		this.onItemClick = onItemClick;
	}

	private void init() {
		View view = inflate(getContext(), R.layout.linear_layout, this);
		ll = (LinearLayout) view.findViewById(R.id.linear_layout);
	}

	public OnItemLongClick getOnItemLongClick() {
		return onItemLongClick;
	}

	public void setOnItemLongClick(OnItemLongClick onItemLongClick) {
		this.onItemLongClick = onItemLongClick;
	}

	public OnItemTouch getOnItemTouch() {
		return onItemTouch;
	}

	public void setOnItemTouch(OnItemTouch onItemTouch) {
		this.onItemTouch = onItemTouch;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

}