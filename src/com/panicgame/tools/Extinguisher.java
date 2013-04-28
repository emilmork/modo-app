package com.panicgame.tools;

import android.content.Context;

import com.panicgame.core.R;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.utils.Util;

public class Extinguisher extends Equipment {
	
	public Extinguisher(int duration,Context context){
		super(duration);
		id = Util.ID_EXTINGUISHER;
		actionItem = new ActionItem(Util.ID_EXTINGUISHER, "Extinguisher"+" ("+duration/1000+"s)", context.getResources().getDrawable(R.drawable.equipment_extinguisher));
		message = "Extinguishes fire!";
	}
	
	@Override
	public boolean canFixEvent(String event){
		return event.equals(FIRE);
	}
	
}
