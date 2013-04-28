package com.panicgame.models;

import java.util.ArrayList;

public class Game {
	public String name;
	public String desc;
	public ArrayList<Player> players;
	public String currentPlayer;
	public String debrief;
		
	public Game(String name, String desc, ArrayList<Player>persons,String currentPlayer,String debrief){
		this.name = name;
		this.desc = desc;
		this.players = persons;
		this.currentPlayer = currentPlayer;
		this.debrief = debrief;
	}
	

	public ArrayList<Player> getPersons() {
		return players;
	}

	public void setPersons(ArrayList<Player> persons) {
		this.players = persons;
	}
	
	public String getDebreaf() {
		return debrief;
	}


	public void setDebreaf(String debrief) {
		this.debrief = debrief;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void addPlayer(Player p){
		players.add(p);
	}



}
