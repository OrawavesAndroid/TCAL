package com.orawaves.tcal.andorid.dto;


@SuppressWarnings("serial")
public class TimelineDTO implements DTO{

	int id;

	String dateAndTime;
	String ctype;
	String content;
	String mShare;
	String mShareEmail;
	String oShare;
	String oShareEmail;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getmShare() {
		return mShare;
	}
	public void setmShare(String mShare) {
		this.mShare = mShare;
	}
	public String getmShareEmail() {
		return mShareEmail;
	}
	public void setmShareEmail(String mShareEmail) {
		this.mShareEmail = mShareEmail;
	}
	public String getoShare() {
		return oShare;
	}
	public void setoShare(String oShare) {
		this.oShare = oShare;
	}
	public String getoShareEmail() {
		return oShareEmail;
	}
	public void setoShareEmail(String oShareEmail) {
		this.oShareEmail = oShareEmail;
	}
	
	
}
