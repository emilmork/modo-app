package com.panicgame.playeractions;

import com.panicgame.core.R;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.utils.Util;

import android.content.Context;

public class CalmDown extends PlayerAction {
	
	public CalmDown(int duration,Context context){
		super(duration);
		actionItem = new ActionItem(Util.ID_ACTION_CALM_PEOPLE, "Calm people down ("+duration/1000+")", context.getResources().getDrawable(R.drawable.calm_down));
	}

}
