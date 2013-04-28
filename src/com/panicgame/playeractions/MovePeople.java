package com.panicgame.playeractions;

import com.panicgame.core.R;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.utils.Util;

import android.content.Context;

public class MovePeople extends PlayerAction {
	public int canCarry = 1;
	public MovePeople(Context context){
		super(0);
		actionItem = new ActionItem(Util.ID_ACTION_MOVE_PEOPLE, "Move people", context.getResources().getDrawable(R.drawable.add_people));
	}
	
	public void setCarry(int carry){
		this.canCarry = carry;
	}
}
