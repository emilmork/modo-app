package com.panicgame.playeractions;

import com.panicgame.core.R;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.utils.Util;

import android.content.Context;

public class HealPeople extends PlayerAction {
	public int procent;
	public HealPeople(int duration,int procent, Context context){
		super(duration);
		this.procent = procent;
		actionItem = new ActionItem(Util.ID_MEDIC, "Heal ("+procent+"%)", context.getResources().getDrawable(R.drawable.equipment_medic));
	}
	
	public int getProcent(){
		return procent;
	}
	

}
