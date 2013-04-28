package com.panicgame.actions;

import java.util.ArrayList;
import java.util.HashMap;

public class GetMap extends Action {
	public static String NAME = "getMap";
	
	public GetMap(){
		super(NAME);
	}
	public GetMap(HashMap<String,String>params){
		super(NAME,params);
	}

}