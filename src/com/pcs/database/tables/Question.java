package com.pcs.database.tables;

import com.pcs.enums.Day;
import com.turbomanage.storm.api.Entity;

@Entity
public class Question {
	private long id;
	private String text;
	private String availableDays;
	private int monday = -1;
	private int tuesday = -1;
	private int wednesday = -1;
	private int thursday = -1;
	private int friday = -1;
	private int saturday = -1;
	private int sunday = -1;

	public int getMonday() {
		return monday;
	}

	public void setMonday(int monday) {
		this.monday = monday;
	}

	public int getTuesday() {
		return tuesday;
	}

	public void setTuesday(int tuesday) {
		this.tuesday = tuesday;
	}

	public int getWednesday() {
		return wednesday;
	}

	public void setWednesday(int wednesday) {
		this.wednesday = wednesday;
	}

	public int getThursday() {
		return thursday;
	}

	public void setThursday(int thursday) {
		this.thursday = thursday;
	}

	public int getFriday() {
		return friday;
	}

	public void setFriday(int friday) {
		this.friday = friday;
	}

	public int getSaturday() {
		return saturday;
	}

	public void setSaturday(int saturday) {
		this.saturday = saturday;
	}

	public int getSunday() {
		return sunday;
	}

	public void setSunday(int sunday) {
		this.sunday = sunday;
	}

	public Question() {

	}

	public Question(String text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}

	public void setOrderForDay(int order, Day day) {
		if (day == Day.MONDAY) {
			setMonday(order);
			return;
		}
		if (day == Day.TUESDAY) {
			setTuesday(order);
			return;
		}
		if (day == Day.WEDNESDAY) {
			setWednesday(order);
			return;
		}
		if (day == Day.THURSDAY) {
			setThursday(order);
			return;
		}
		if (day == Day.FRIDAY) {
			setFriday(order);
			return;
		}
		if (day == Day.SUNDAY) {
			setSunday(order);
			return;
		}
		if (day == Day.SATURDAY) {
			setSaturday(order);
			return;
		}
		throw new IllegalArgumentException("Such day " + day.toString()
				+ "is not served");
	}

	public int getOrderForDay(Day day) {
		if (day == Day.MONDAY) {
			return getMonday();
		}
		if (day == Day.TUESDAY) {
			return getTuesday();
		}
		if (day == Day.WEDNESDAY) {
			return getWednesday();
		}
		if (day == Day.THURSDAY) {
			return getThursday();
		}
		if (day == Day.FRIDAY) {
			return getFriday();
		}
		if (day == Day.SUNDAY) {
			return getSunday();
		}
		if (day == Day.SATURDAY) {
			return getSaturday();
		}
		throw new IllegalArgumentException("Such day " + day.toString()
				+ "is not served");
	}
}
