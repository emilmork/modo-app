package com.panicgame.models;

import java.util.ArrayList;

public class Map {
	
	private ArrayList<ArrayList<Sector>>sectors;
	
	public Map(ArrayList<ArrayList<Sector>>sectors){
		this.sectors = sectors;
	}
	public Map(){
		
	}

	public ArrayList<ArrayList<Sector>> getSectors() {
		return sectors;
	}

	public void setSectors(ArrayList<ArrayList<Sector>> sectors) {
		this.sectors = sectors;
	}
	
	public Sector getSector(String player_id){
		for(ArrayList<Sector> sector_list : sectors){
			for(Sector s : sector_list){
				for(Player player : s.getPlayers()){
					if(player.getId().equals(player_id))return s;
				}
			}
		}
		return null;
	}
	
	
	
	
	
}
