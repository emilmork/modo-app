package com.panicgame.actions;

import java.util.ArrayList;
import java.util.HashMap;

public class GetTeams extends Action {
	public static String NAME = "getTeams";
	
	public GetTeams(){
		super(NAME);
	}
	public GetTeams(HashMap<String,String>params){
		super(NAME,params);
	}

}
