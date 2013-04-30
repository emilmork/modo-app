package com.panicgame.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.panicgame.managers.InputManager;
import com.panicgame.models.Game;
import com.panicgame.models.Map;
import com.panicgame.models.Player;
import com.panicgame.models.Sector;
import com.panicgame.models.Team;

import android.app.Application;
import android.graphics.Point;
import android.util.Log;

public class ApplicationObject extends Application {
	public Player me;
	public ArrayList<Team> teams;
	public Game current_game;
	public Map map;
	public ArrayList<ArrayList<Sector>>sectors;

	public TCPClient client = null;

	@Override
	public void onCreate() {
		super.onCreate();
		teams = new ArrayList<Team>();
		sectors = new ArrayList<ArrayList<Sector>>();
	}
	
	
	
	public Sector getSector(int position_id){
		for(ArrayList<Sector> s : map.getSectors()){
			for(Sector sector : s){
				if(sector.getId()==position_id){
					return sector;
				}
			}
		}
		return null;
	}
	

}
