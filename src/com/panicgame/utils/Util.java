package com.panicgame.utils;

public class Util {
	
	public static String TEAM_POSITION = "team_position";
	public static String GAME_POSITION = "game_position";
	
	//DEBUG
	public static final int JOINE_GAME_DIALOG = 1;
	public static final String RESPONSE = "response";
	
	//URLS"http://192.168.0.133:8000/getTeams"
	public static final String SERVER_URL = "192.168.0.133";
	public static final int SERVER_PORT = 5000;
	
	/**
	 * RESPONSE
	 */
	
	public static final int RES_TEAMS = 1;
	public static final int RES_MESSAGE = 2;
	public static final int RES_MAP = 3;
	public static final int RES_PLAYER_ID = 4;
	public static final int RES_YOUR_TURN = 5;
	public static final int RES_PANIC_INCREASED = 6;
	
	public static final String RESPONSE_CONTEXT = "context";
	public static final String RESPONSE_TEAMS = "teams";
	public static final String RESPONSE_MAP = "map";
	public static final String RESPONSE_PLAYER_ID = "playerid";
	public static final String RESPONSE_MESSAGE = "message";
	public static final String RESPONSE_YOUR_TURN = "yourturn";
	public static final String RESPONSE_PANIC_INCREASE = "panic_increased";
	public static final String RESPONSE_DEBREAF = "debreaf";
	/**
	 * ImageView types
	 */
	
	public static final int ME = 1;
	public static final int TEAM_MEMBER = 2;
	public static final int PERSON = 3;
	public static final int DUMMY = 4;
	
	/**
	 * Text types
	 *
	 */
	public static final int SECTOR_NAME = 5;
	public static final int SECTOR_PANIC = 6;
	public static final int NUM_CIVIL = 7;
	
	/**
	 * Actions
	 * 
	 */
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_MOVE_PEOPLE = 2;
	public static final int ACTION_CALM_DOWN_PEOPLE = 3;
	
	/**
	 * Action shortcuts
	 */
	public static final String SEMI_COLON = ";";
	public static final String DO_ACTION_MOVE = "doAction;move";
	public static final String DO_ACTION_CALM_DOWN = "doAction;calmDownPeople";
	public static final String DO_ACTION_MOVE_PEOPLE = "doAction;movePeople";
	public static final String DO_ACTION_DONE = "doAction;done;";
	
	
	/**
	 * QUICK POPUP IDS
	 * 
	 */
	
	public static final int ID_HAMMER = 2;
	public static final int ID_EXTINGUISHER = 1;
	public static final int ID_MEDIC = 3;
	public static final int ID_ADD_EQUIPMENT = 4;
	
	public static final int ID_ACTION_MOVE_PEOPLE = 5;
	public static final int ID_ACTION_CALM_PEOPLE = 6;
	public static final int ID_ACTION_DROP_PEOPLE = 7;
	public static final int ID_ACTION_BROADCAST = 8;
	
	public static final int ID_LOCATION_SET = 9;
	
	/**
	 * Broadcast response
	 */
	
	public static final String NEED_HELP= "need_help";
	
	

}
