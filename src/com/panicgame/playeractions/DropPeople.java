package com.panicgame.playeractions;

import android.content.Context;

import com.panicgame.core.R;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.utils.Util;

public class DropPeople extends PlayerAction {
	public DropPeople(Context context){
		super(0);
		actionItem = new ActionItem(Util.ID_ACTION_DROP_PEOPLE, "Drop civilian", context.getResources().getDrawable(R.drawable.peoples));
	}
}
