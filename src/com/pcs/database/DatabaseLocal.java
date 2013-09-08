package com.pcs.database;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.pcs.database.tables.ImageContent;
import com.pcs.database.tables.dao.ImageContentDao;
import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.TableHelper;
import com.turbomanage.storm.api.DatabaseFactory;


@com.turbomanage.storm.api.Database(name = "databaseLocal", version = 1)
public class DatabaseLocal extends DatabaseHelper {

	private static final String PCS_IMAGES = "pcs_images";

	public DatabaseLocal(Context ctx, DatabaseFactory dbFactory) {
		super(ctx, dbFactory);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
		initialValuesForImageContent(db);
	}

	@Override
	public UpgradeStrategy getUpgradeStrategy() {
		return UpgradeStrategy.DROP_CREATE;
	}

	public void initialValuesForImageContent(SQLiteDatabase db) {
		ImageContentDao imageDao = new ImageContentDao(getContext());
		AssetManager asset = getContext().getAssets();
		try {
			String[] allImages = asset.list(PCS_IMAGES);
			for (String imagePath : allImages) {// ImageContent imageContent =
												// new
				ImageContent imageContent = new ImageContent();
				imageContent.setImageLink(PCS_IMAGES + "/" + imagePath);
				@SuppressWarnings("unchecked")
				TableHelper<ImageContent> th = imageDao.getTableHelper();
				ContentValues cv = th.getEditableValues(imageContent);
				if (th.getId(imageContent) == 0) {
					// the default, remove from ContentValues to allow autoincrement
					cv.remove(th.getIdCol().toString());
				}
				db.insertOrThrow(th.getTableName(), null, cv);
			}
		} catch (IOException e) {

		}
	}


}

