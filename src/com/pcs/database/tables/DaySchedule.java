package com.pcs.database.tables;

import com.turbomanage.storm.api.Entity;

@Entity
public class DaySchedule {
	private long id;
	private long childID;

	private enum Day {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	};

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChildID() {
		return childID;
	}

	public void setChildID(long childID) {
		this.childID = childID;
	}
}
