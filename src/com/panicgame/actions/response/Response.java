package com.panicgame.actions.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.panicgame.actions.Action;
import com.panicgame.actions.GetTeams;
import com.panicgame.models.Sector;
import com.panicgame.models.Team;
import com.panicgame.utils.Util;

public class Response {
	private HashMap<String, String> response;
	private Gson gson;

	public Response() {
		gson = new Gson();
		response = new HashMap<String, String>();
	}

	public boolean parse(String message)  {
		try {
			response = (HashMap<String, String>) gson.fromJson(message, new TypeToken<HashMap<String, String>>() {}.getType());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void clear(){
		response = new HashMap<String, String>();
		gson = new Gson();
	}

	public String getContent(String param) {
		Iterator it = response.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String key = (String) pairs.getKey();
			if (key.equals(param)) {
				return (String)pairs.getValue();
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
		return null;
	}
	public HashMap getRespons(){
		return response;
	}

}
