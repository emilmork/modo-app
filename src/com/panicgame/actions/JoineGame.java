package com.panicgame.actions;

import java.util.ArrayList;
import java.util.HashMap;

public class JoineGame extends Action {
	public static String NAME = "playerJoine";
	
	public JoineGame(){
		super(NAME);
	}
	public JoineGame(HashMap<String,String>params){
		super(NAME,params);
	}

}
