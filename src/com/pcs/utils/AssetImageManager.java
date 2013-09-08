package com.pcs.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AssetImageManager {
	private AssetManager assetManager;

	public AssetImageManager(Context ctx) {
		assetManager = ctx.getAssets();
	}

	public Drawable getImage(String pathToAssets) throws IOException {
		InputStream stream = assetManager.open(pathToAssets);
		Drawable d = Drawable.createFromStream(stream, null);
		return d;
	}

	public Drawable getResizeImage(String pathToAssets) throws IOException {
		InputStream stream = assetManager.open(pathToAssets);
		Drawable d = Drawable.createFromStream(stream, null);
		Bitmap b = ((BitmapDrawable) d).getBitmap();
		Bitmap bitmapResized = Bitmap.createScaledBitmap(b, b.getWidth() / 2,
				b.getHeight() / 2, true);
		return new BitmapDrawable(bitmapResized);
	}

}
