package com.pcs.database.tables;

import com.turbomanage.storm.api.Entity;

@Entity
public class DayScheduleImage {
	private long id;
	private long dayScheduleID;
	private String link;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDayScheduleID() {
		return dayScheduleID;
	}

	public void setDayScheduleID(long dayScheduleID) {
		this.dayScheduleID = dayScheduleID;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
