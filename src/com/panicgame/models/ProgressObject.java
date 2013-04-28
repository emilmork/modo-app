package com.panicgame.models;

import com.panicgame.actions.Action;

public class ProgressObject {
	public Action action;
	public int duration;
	public String message;
	public Sector sector;
	
	public ProgressObject(Action action, int duration,String message,Sector sector){
		this.action = action;
		this.duration = duration;
		this.message = message;
		this.sector = sector;
	}
	

	public Sector getSector(){
		return sector;
	}
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
