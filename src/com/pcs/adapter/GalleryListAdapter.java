package com.pcs.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.pcs.communicator.R;
import com.pcs.database.tables.ImageContent;
import com.pcs.utils.AssetImageManager;

public class GalleryListAdapter extends BaseAdapter {

	private List<ImageContent> content;
	private LayoutInflater inflanter;
	private AssetImageManager assetManager;

	static class ViewHolder {
		public ImageView image;
	}

	public GalleryListAdapter(Context ctx, List<ImageContent> content) {
		this.content = content;
		inflanter = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		assetManager = new AssetImageManager(ctx);
	}

	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public ImageContent getItem(int position) {
		return content.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View item = convertView;
		ViewHolder viewHolder;
		ImageContent imageContent = content.get(position);
		if (item == null) {
			item = inflanter.inflate(R.layout.gallery_item_view, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) item.findViewById(R.id.pcs_image);
			item.setTag(viewHolder);
		} 
		viewHolder = (ViewHolder) item.getTag();
		try {
			viewHolder.image.setImageDrawable(assetManager
					.getResizeImage(imageContent.getImageLink()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}
}
