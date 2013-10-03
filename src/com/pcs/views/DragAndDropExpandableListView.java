package com.pcs.views;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

public class DragAndDropExpandableListView extends ExpandableListView{

	private DragDropHandlerListener dragDropHandlerListener;
	public static final String CHANGE_ORDER = "changeOrder";

	public interface DragDropHandlerListener {
		public void handelDropAction(View selectedView, View dropAreaView);
	}

	private class LongClickItemListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			ClipData.Item item = new ClipData.Item(CHANGE_ORDER);
			ClipData data = new ClipData(CHANGE_ORDER,
					new String[] { CHANGE_ORDER }, item);

			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);
			return true;
		}
	}

	private class DragListener implements OnDragListener {

		@Override
		public boolean onDrag(View dropAreaView, DragEvent event) {
			View selectedView = (View) event.getLocalState();
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				if (event.getClipDescription().getMimeType(0)
						.equals(CHANGE_ORDER)) {
					return true;
				}
				return false;
			case DragEvent.ACTION_DRAG_ENTERED:
				dropAreaView.setBackgroundColor(Color.GRAY);
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				dropAreaView.setBackgroundDrawable(null);
				break;
			case DragEvent.ACTION_DROP:
				dragDropHandlerListener.handelDropAction(selectedView, dropAreaView);
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				dropAreaView.setBackgroundDrawable(null);
			default:
				break;
			}
			return true;
		}
	}

	public DragAndDropExpandableListView(Context context) {
		super(context);
		init();
	}
	
	public DragAndDropExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DragAndDropExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setOnItemLongClickListener(new LongClickItemListener());
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			if (child != null) {
				child.setOnDragListener(new DragListener());
			}
		}
	}

	public DragDropHandlerListener getDragDropHandlerListener() {
		return dragDropHandlerListener;
	}

	public void setDragDropHandlerListener(
			DragDropHandlerListener dragDropHandlerListener) {
		this.dragDropHandlerListener = dragDropHandlerListener;
	}
}
