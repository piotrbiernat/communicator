package com.pcs.database;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.api.DatabaseFactory;


@com.turbomanage.storm.api.Database(name = "databaseLocal", version = 1)
public class DatabaseLocal extends DatabaseHelper {

	public DatabaseLocal(Context ctx, DatabaseFactory dbFactory) {
		super(ctx, dbFactory);
	}

	@Override
	public UpgradeStrategy getUpgradeStrategy() {
		return UpgradeStrategy.DROP_CREATE;
	}

}

