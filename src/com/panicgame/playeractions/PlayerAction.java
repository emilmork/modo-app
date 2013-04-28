package com.panicgame.playeractions;

import com.panicgame.popupwindow.ActionItem;

public class PlayerAction {
	public ActionItem actionItem;
	public int duration;
	public String message;
	
	public PlayerAction(int duration){
		this.duration = duration;
	}
	
	public ActionItem getActionItem(){
		return actionItem;
	}
	public int getDuration(){
		return duration;
	}
}
