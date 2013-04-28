package com.panicgame.listeners;

import java.util.ArrayList;

import com.panicgame.models.Game;
import com.panicgame.models.Player;
import com.panicgame.models.Team;

public interface GameListener {
	public void connected();
	public void playerJoined(Player player,Team team, Game game);
	public void playerLeft(Player player);
	public void disconnected();
	public void yourTurn(int roundtime);
	public void gameStarted(int panicintercal);
	public void gameOver(String score);
	public void teamsReceived();
}
