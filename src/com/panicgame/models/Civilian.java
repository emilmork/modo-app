package com.panicgame.models;

import android.provider.SyncStateContract.Helpers;

public class Civilian {
	public final static int OK = 1;
	public final static int MEDIUM = 2;
	public final static int BAD = 3;
	public final static int DEAD = 4;
	
	protected int id;
	protected int health;
	protected String name;
	protected int panic = 0;
	protected boolean checked = false;
	
	public Civilian(int id,String name, int health,int panic){
		this.id = id;
		this.health = health;
		this.name = name;
		this.panic = panic;
	}
	
	public void setPanic(int panic){
		this.panic = panic;
	}
	public int getPanic(){
		return panic;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void setChecked(boolean checked){
		this.checked = checked;
	}
	public boolean isChecked(){
		return checked;
	}
	
	public int getCivilianStatus(){
		if(health<=50 && health>10 && panic>5){
			return BAD;
		}else if(health<=75 && health >=50 && panic >=4){
			return MEDIUM;
		}else if(health > 75 && panic < 4){
			return OK;
		}else if(health <= 10){
			return DEAD;
		}else{
			return MEDIUM;
		}
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getId(){
		return id;
	}

}
