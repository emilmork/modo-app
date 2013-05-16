package com.panicgame.models;

import java.util.ArrayList;

import com.panicgame.playeractions.Broadcast;
import com.panicgame.playeractions.CalmDown;
import com.panicgame.playeractions.HealPeople;
import com.panicgame.playeractions.MovePeople;
import com.panicgame.playeractions.PlayerAction;
import com.panicgame.tools.Equipment;
import com.panicgame.tools.Extinguisher;
import com.panicgame.tools.Hammer;

import android.content.Context;
import android.util.Log;

public class Player {
	private String name;
	private String role;
	private String id;
	private int postion_id;
	public int actionsLeft = 3;
	public int movementsLeft = 2;
	public ArrayList<Integer>exploredSectors;
	public ArrayList<Integer>checkedCivilians;
	
	public ArrayList<Equipment>equipments2;
	public ArrayList<PlayerAction>player_actions;
	
	public ArrayList<PlayerAction>temp_actions = new ArrayList<PlayerAction>();

	private ArrayList<Civilian>attachedCivils;
	
	public int getPostion_id() {
		return postion_id;
	}

	public void setPostion_id(int postion_id) {
		this.postion_id = postion_id;
	}
	
	public Player(String name, String id,String role,ArrayList<Integer>actions){
		this.name = name;
		this.id = id;
		this.role = role;
		Log.i("Player constructor. Actions",""+actions.size());
	}

	public Player(String name){
		this.name = name;
		attachedCivils = new ArrayList<Civilian>();
		exploredSectors = new ArrayList<Integer>();
		checkedCivilians = new ArrayList<Integer>();
		
		//*****
		equipments2 = new ArrayList<Equipment>();
		player_actions = new ArrayList<PlayerAction>();
	}
	
	public void initEquipment(Context context){
		equipments2.add(new Extinguisher(10000,context));
		equipments2.add(new Hammer(10000,context));
	}
	
	public void initActions(Context context){
		temp_actions.add(new MovePeople(context));
		temp_actions.add(new CalmDown(5000, context));
		temp_actions.add(new HealPeople(5000, 20, context));
		temp_actions.add(new Broadcast(context));
	}
	
	public void refreshActions(){
		for(PlayerAction action : temp_actions){
			player_actions.add(action);
		}
	}
	
	public ArrayList<PlayerAction>getActions(){
		return player_actions;
	}
	
	public void removeActions(){
		player_actions.clear();
	}
	
	public void addAction(PlayerAction action){
		player_actions.add(action);
	}

	public String getName() {
		return name;
	}
	
	public boolean hasEquipment(int equipment){
		boolean hasEquipment = false;
		for(Equipment e : equipments2){
			if(e.id == equipment)hasEquipment=true;
		}
		return hasEquipment;
	}
	
	
	public void addEquipment(Equipment equipment){
		this.equipments2.add(equipment);
	}
	
	
	//******TEST
	public ArrayList<Equipment>getEquipments(){
		return equipments2;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSectorExplored(int sectorId){
		for(int i : exploredSectors){
			if(i==sectorId)return true;
		}
		return false;
	}
	
	public ArrayList<Civilian>getAttachedCivilians(){
		return attachedCivils;
	}
	
	public ArrayList<Integer>getCheckedCivilians(){
		return checkedCivilians;
	}
	
	public boolean civilianChecked(int id){
		if(checkedCivilians.size()<2){
			checkedCivilians.add(id);
			return true;
		}
		Log.i("Civil","civlianChecked[Player] " + id);
		return false;
	}
	public void civilianNotChecked(int id){
		boolean remove = false;
		for(int civilian_id : checkedCivilians){
			if(civilian_id==id)remove=true;
		}
		if(remove){
			checkedCivilians.remove(checkedCivilians.indexOf(id));
			Log.i("Civil","civlian NOT Checked[Player] " + id);
		}
	}
	
	public boolean isCivilianChecked(int id){
		for(int civilian_id : checkedCivilians){
			if(civilian_id==id)return true;
		}
		return false;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setId(String id) {
		this.id = id;	
	}
	
	public void attacheCivil(Civilian civil){
		civil.setChecked(false);
		attachedCivils.add(civil);
	}
	
	public void dropCivilians(){
		attachedCivils.clear();
		checkedCivilians.clear();
	}
	
	public String getId(){
		return id;
	}
}
