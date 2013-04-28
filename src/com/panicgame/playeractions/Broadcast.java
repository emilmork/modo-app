package com.panicgame.playeractions;

import com.panicgame.core.R;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.utils.Util;

import android.content.Context;

public class Broadcast extends PlayerAction {
	public Broadcast(Context context){
		super(0);
		actionItem = new ActionItem(Util.ID_ACTION_BROADCAST, "Bradcast", context.getResources().getDrawable(R.drawable.walkie_talkie));
	}
}
