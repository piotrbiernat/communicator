package com.pcs.entities;

import com.turbomanage.storm.api.Entity;
import com.turbomanage.storm.api.Id;

@Entity
public class DayScheduleEntity {

	@Id
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
