package com.pcs.database;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.api.DatabaseFactory;

@com.turbomanage.storm.api.Database(name = "application", version = 1)
public class Database extends DatabaseHelper {

	public Database(Context ctx, DatabaseFactory dbFactory) {
		super(ctx, dbFactory);
	}

	@Override
	public UpgradeStrategy getUpgradeStrategy() {
		return UpgradeStrategy.DROP_CREATE;
	}

}
