package com.panicgame.roles;
import android.content.Context;

import com.panicgame.models.Player;
import com.panicgame.playeractions.Broadcast;
import com.panicgame.playeractions.CalmDown;
import com.panicgame.playeractions.HealPeople;
import com.panicgame.playeractions.MovePeople;
import com.panicgame.tools.Extinguisher;
import com.panicgame.tools.Hammer;

public class Volunteer extends Player {
	
	public Volunteer(String name){
		super(name);
		movementsLeft = 3;
		actionsLeft = 3;
	}
	
	@Override
	public void initEquipment(Context context){
		equipments2.add(new Extinguisher(10000,context));
	}
	
	@Override
	public void initActions(Context context){
		player_actions.add(new MovePeople(context));
		player_actions.add(new HealPeople(10000, 10, context));
		player_actions.add(new CalmDown(10000, context));
		player_actions.add(new Broadcast(context));
	}
	
	

	
}
	
