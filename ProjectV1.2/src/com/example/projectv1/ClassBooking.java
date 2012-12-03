package com.example.projectv1;


public class ClassBooking {
	private String uid;
	private String summary;
	private String startTime;
	private String endTime;
	private String url;


	public ClassBooking(String uid, String summary, String startTime,
			String endTime, String url) {
		this.uid = uid;
		this.summary = summary;
		this.startTime = startTime;
		this.endTime = endTime;
		this.url = url;
	}

	public String getUID() {
		return uid;
	}

	public void setUID(final String uid) {
		this.uid = uid;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(final String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(final String endTime) {
		this.endTime = endTime;
	}
	
	
	public String getURL() {
		return url;
	}

	public void setURL(final String url) {
		this.url = url;
	}

}
