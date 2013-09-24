package com.pcs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AssetImageManager {
	private Map<String, BitmapDrawable> buffor = new HashMap<String, BitmapDrawable>();

	private AssetManager assetManager;
	private float desity;
	private int width;
	private int height;

	public AssetImageManager(Context ctx) {
		assetManager = ctx.getAssets();
		desity = ctx.getResources().getDisplayMetrics().density;
		width = (int)(desity*120);
		height = (int)(desity*120);
		
	}

	public Drawable getImage(String pathToAssets) throws IOException {
		InputStream stream = assetManager.open(pathToAssets);
		Drawable d = Drawable.createFromStream(stream, null);
		stream.close();
		return d;
	}

	public Drawable getResizeImage(String pathToAssets) throws IOException {
		if (!buffor.containsKey(pathToAssets)) {
			InputStream stream = assetManager.open(pathToAssets);
			Drawable d = Drawable.createFromStream(stream, null);
			Bitmap b = ((BitmapDrawable) d).getBitmap();
			Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, true);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmapResized);
			buffor.put(pathToAssets, bitmapDrawable);
		}
		return buffor.get(pathToAssets);
	}

}
