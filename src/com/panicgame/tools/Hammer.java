package com.panicgame.tools;

import com.panicgame.core.R;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.utils.Util;

import android.content.Context;

public class Hammer extends Equipment {
	
	public Hammer(int duration,Context context){
		super(duration);
		id = Util.ID_HAMMER;
		actionItem = new ActionItem(Util.ID_HAMMER, "Hammer"+" ("+duration/1000+"s)", context.getResources().getDrawable(R.drawable.equipment_hammer));
		message = "Smashing door!";
	}
	
	@Override
	public boolean canFixEvent(String event){
		return event.equals(LOCKED_DOOR);
	}

}
