package com.panicgame.actions;

public class BroadcastMessage extends Action {
	public static final String ACTION = "broadcastMessage";
	public int type = 0;
	
	public BroadcastMessage(){
		super(ACTION);
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
}
