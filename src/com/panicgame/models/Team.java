package com.panicgame.models;

import java.util.ArrayList;

public class Team {
	public String name;
	public ArrayList<Game>games;
	
	public Team(String name,ArrayList<Game>games){
		this.name = name;
		this.games = games;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Game> getGames() {
		return games;
	}
	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}


}
