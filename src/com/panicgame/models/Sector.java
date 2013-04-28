package com.panicgame.models;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

public class Sector {
	public static final int NAME = 100;
	public static final int PANIC = 200;
	public static final int CIVILS = 300;
	public static final int CIVILS_IMAGE = 400;
	public static final int ME = 500;
	public static final int TEAM_MEMBER = 600;
	
	private String nm;
	private int pl;
	private ArrayList<Civilian> cls;
	public int id;
	public String event;
	public ArrayList<String>pls = new ArrayList<String>();
	public ArrayList<Integer> eq = new ArrayList<Integer>();
	public boolean safe = false;
	
	public Sector(String nm,int pl,int id,ArrayList<Civilian> cls,ArrayList<String>pls,String event,boolean safe,ArrayList<Integer>eq){
		
		this.nm = nm;
		this.pl = pl;
		this.id = id;
		this.cls = cls;
		this.pls = pls;
		this.event = event;
		this.safe = safe;
		this.eq = eq;
		if(safe)this.nm = this.nm+ " (Safe sector)";
		
	}
	
	public void removeEquipment(int type){
		Iterator<Integer>i = eq.iterator();
		while(i.hasNext()){
			if(type==i.next()){
				i.remove();
				break;
			}
		}
	}
	
	public ArrayList<Integer> getEq(){
		return eq;
	}
	
	public void setEq(ArrayList<Integer>equipment){
		this.eq = equipment;
	}
	
	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public void removePlayer(String id){
		pls.remove(id);
	}
	
	public void addPlayer(String id){
		pls.add(id);
	}
	
	public void setEvent(String event){
		this.event = event;
	}
	
	public String getEvent(){
		return event;
	}
	
	public ArrayList<String> getPlayers() {
		return pls;
	}

	public void setPlayers(ArrayList<String> pls) {
		this.pls = pls;
	}

	public void setCivils(ArrayList<Civilian> civils) {
		this.cls = civils;
	}
	
	
	public ArrayList<Civilian>getCivilians(){
		return cls;
	}

	public String getName() {
		return nm;
	}
	

	public void setName(String name) {
		this.nm = name;
	}

	public int getPaniclevel() {
		if(cls.size()==0 || nm.equals("Start"))return 0;
		int civil_panic=0;
		for(Civilian c : cls){
			civil_panic+=c.getCivilianStatus();
		}
		return civil_panic/cls.size();
	}

	public void setPaniclevel(int paniclevel) {
		this.pl = paniclevel;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	
	public int getCivils(){
		return cls.size();
	}
	/**
	public void reduceCivils(int number){
		cls = (cls-2) > 0 ? cls-=2 : 0; 
	}
	**/
	
	/*
	 * Get IDS for Views based on type
	 * 
	 */
	public int getMeImageID(){
		return ME+id;
	}
	
	public int getCivilImageID(){
		return CIVILS_IMAGE+id;
	}
	
	public int getCivilCountID(){
		return CIVILS+id;
	}
	
	public int getNameID(){
		return NAME+id;
	}
	public int getPanicLevelID(){
		return PANIC+id;
	}
	public int getTeamMemberImageID(){
		return TEAM_MEMBER+id;
	}

	
}
