package com.panicgame.actions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

public class Action {
	public String action = null;
	public HashMap<String,String>params = new HashMap<String, String>();
	
	public Action(String action,HashMap<String,String>params){
		this.action = action;
		this.params = params;
		params.put("json", "true");
	}
	public Action(String actionName){
		this.action = actionName;
		params.put("json", "true");
	}
	public void addParam(String key,String value){
		params.put(key,value);
	}
	
	public String getAction(){
		Gson gson = new Gson();	
		return gson.toJson(this);
	}
	
	public boolean hasParam(String key,String value){
		boolean hasParam = false;
		Iterator it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if(pairs.getKey().equals(key) && pairs.getValue().equals(value)){
				hasParam = true;
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
		return hasParam;
	}
}
