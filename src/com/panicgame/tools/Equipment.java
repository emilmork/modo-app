package com.panicgame.tools;

import android.content.Context;
import com.panicgame.popupwindow.ActionItem;

public class Equipment {
	
	public static String FIRE = "fire";
	public static String LOCKED_DOOR = "locked";
	public int duration;
	public String message = "";
	public ActionItem actionItem;
	public int id;
	
	public Equipment(int duration){
		this.duration = duration;
	}
	
	public ActionItem getActionItem(){
		return actionItem;
	}
	
	public int getDuration(){
		return duration;
	}
	public String getMessage(){
		return message;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public boolean canFixEvent(String event){
		return false;
	}

}
