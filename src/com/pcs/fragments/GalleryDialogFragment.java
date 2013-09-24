package com.pcs.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.pcs.adapter.GalleryListAdapter;
import com.pcs.communicator.R;
import com.pcs.database.tables.ImageContent;
import com.pcs.database.tables.dao.ImageContentDao;
import com.pcs.utils.DoNothigClickListener;

public class GalleryDialogFragment extends DialogFragment {

	private GridView gridView;
	private ImageContentDao imageContentDao;
	private View rootView;
	private GalleryDialogSelection galleryDialogSelection;
	private List<ImageContent> selectedImages = new ArrayList<ImageContent>();

	public interface GalleryDialogSelection {
		public void selectedImages(List<ImageContent> imagesContent);
	}

	private class ImageClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			GalleryListAdapter adapter = (GalleryListAdapter) gridView
					.getAdapter();
			ImageContent selectedImage = adapter.getItem(position);
			if (selectedImages.contains(selectedImage)) {
				selectedImages.remove(selectedImage);
				view.setBackgroundDrawable(null);
			} else {
				selectedImages.add(selectedImage);
				view.setBackgroundColor(getResources().getColor(
						R.color.selected));
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		rootView = getActivity().getLayoutInflater().inflate(
				R.layout.gallery_view, null);
		gridView = (GridView) rootView.findViewById(R.id.gridWithImages);
		gridView.setAdapter(new GalleryListAdapter(getActivity(),
				imageContentDao.listAll()));
		gridView.setOnItemClickListener(new ImageClickListener());

		builder.setView(rootView);
		builder.setPositiveButton(R.string.save, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(getGalleryDialogSelection() != null ) {
					getGalleryDialogSelection().selectedImages(selectedImages);
				}
			}
		});
		builder.setNegativeButton(android.R.string.cancel,
				new DoNothigClickListener());
		return builder.create();
	}

	public ImageContentDao getImageContentDao() {
		return imageContentDao;
	}

	public void setImageContentDao(ImageContentDao imageContentDao) {
		this.imageContentDao = imageContentDao;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof GalleryDialogSelection) {
			setGalleryDialogSelection((GalleryDialogSelection) activity);
		}
	}

	public GalleryDialogSelection getGalleryDialogSelection() {
		return galleryDialogSelection;
	}

	public void setGalleryDialogSelection(GalleryDialogSelection galleryDialogSelection) {
		this.galleryDialogSelection = galleryDialogSelection;
	}
}
