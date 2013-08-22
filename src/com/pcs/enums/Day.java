package com.pcs.enums;

import com.pcs.communicator.R;

public enum Day {

	MONDAY(R.string.monday), TUESDAY(R.string.tuesday), WEDNESDAY(
			R.string.wednesday), THURSDAY(R.string.thursday), FRIDAY(
			R.string.friday), SATURDAY(R.string.saturday), SUNDAY(
			R.string.sunday);

	private int resourceDescription;

	private Day(int resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	public int getResourceID() {
		return resourceDescription;
	}

}
