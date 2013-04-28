package com.panicgame.listeners;


public interface EventListener {
	public void mapUpdate();
	public void panicIncreased(int newinterval);
	public void broadcastMessageReceived(String title,String message);
}
