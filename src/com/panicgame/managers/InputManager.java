package com.panicgame.managers;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.panicgame.actions.GetMap;
import com.panicgame.actions.response.Response;
import com.panicgame.core.ApplicationObject;
import com.panicgame.listeners.EventListener;
import com.panicgame.listeners.GameListener;
import com.panicgame.models.Game;
import com.panicgame.models.Player;
import com.panicgame.models.Sector;
import com.panicgame.models.Team;
import com.panicgame.utils.Util;

public class InputManager {

	private static InputManager inputManager;
	private Gson gson;
	private static Context context;
	private static ApplicationObject app;
	public ArrayList<GameListener> listeners;
	public ArrayList<EventListener> eventListeners;

	private InputManager(Context context) {
		gson = new Gson();
		app = (ApplicationObject) context.getApplicationContext();
		listeners = new ArrayList<GameListener>();
		eventListeners = new ArrayList<EventListener>();
	}

	public static InputManager getInstance(Context context) {
		if (inputManager == null) {
			inputManager = new InputManager(context);
			return inputManager;
		} else {
			return inputManager;
		}
	}

	public void handleInput(String message) {
		Log.i("INPUT", message);
		Response response = new Response();
		if (!response.parse(message)) {
			Log.i("Response", "Could not parse message");
		} else {
			Iterator it = response.getRespons().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				String key = (String) pairs.getKey();

				if (key.equals(Util.RESPONSE_TEAMS)) {

					Log.i("UPDATE", "PARSING TEMAS");
					Log.i("TEAM", response.getContent(Util.RESPONSE_TEAMS));

					try {
						app.teams = gson.fromJson(response.getContent(Util.RESPONSE_TEAMS), new TypeToken<ArrayList<Team>>() {
						}.getType());
						fireTeamsReceived();
					} catch (Exception e) {
						e.printStackTrace();
					}
					Log.i("UPDATE", "TEAMS RECEIVED AND PARSED");

				} else if (key.equals(Util.RESPONSE_MAP)) {

					try {
						app.map = gson.fromJson(response.getContent(Util.RESPONSE_MAP), new TypeToken<com.panicgame.models.Map>() {
						}.getType());
						fireMapUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}

					Log.i("UPDATE", "TEAMS RECEIVED AND PARSED");
					Log.i("UPDATE", response.getContent(Util.RESPONSE_MAP));

				} else if (key.equals(Util.RESPONSE_MESSAGE)) {
					String res = (String) pairs.getValue();

					if (pairs.getValue().equals("update")) {

						app.client.write(new GetMap().getAction());

					} else if (res.contains("startgame")) {
						String[] parts = res.split(";");
						fireGameStarted(parts[1]);

					} else if (res.contains("yourturn")) {
						String[] parts = res.split(";");
						fireYourTurn(parts[1]);
					} else if (res.contains("panicwave")) {
						String[] parts = res.split(";");
						firePanicIncreased(parts[1]);

					} else if (res.contains("gameOver")) {
						String[] parts = res.split(";");

						fireGameOver(parts[1]);
					} else if (res.contains("broadcast")) {
						String[] parts = res.split(";");
						parseBroadCastMessage(parts);
						Log.i("Broadcast", res);
					}

					Log.i("UPDATE", "MESSAGE RECEIVED AND PARSED");

				} else if (key.equals(Util.RESPONSE_PLAYER_ID)) {

					Log.i("UPDATE", "PLAYER ID RECEIVED AND PARSED");

					String id = response.getContent(Util.RESPONSE_PLAYER_ID);
					Sector start_sector = app.map.getSector(id);
					app.me.exploredSectors.add(start_sector.getId());
					app.me.setId(id);
					app.me.setPostion_id(start_sector.getId());

					fireMapUpdate();
				}
				it.remove(); // avoids a ConcurrentModificationException
			}

		}

	}

	private void parseBroadCastMessage(String[] parts) {
		if (parts[2].equals(app.me.getId()))
			return;

		fireBroadCastMessage("Message", parts[1]);
	}

	public void playerJoined(String[] parts) {

		Player p = new Player(parts[3]);

		for (Team t : app.teams) {
			if (t.getName().equals(parts[1])) {
				for (Game g : t.getGames()) {
					if (g.getName().equals(parts[2])) {
						g.addPlayer(p);
						firePlayerJoined(p, t, g);
					}
				}
			}
		}

	}

	public void playerLeft(String[] parts) {
		for (Team t : app.teams) {
			for (Game g : t.getGames()) {
				if (parts[1].equals(g.getName())) {
					for (Player p : g.getPersons()) {
						if (parts[2].equals(p.getName())) {
							firePlayerLeft(p);
						}
					}
				}
			}
		}
	}

	/** FIRE GAME EVENTLISTENER EVENTS ***/

	public void fireMapUpdate() {
		Log.i("UPDATE", "fireMapUpdate()");
		for (EventListener e : eventListeners) {
			e.mapUpdate();
		}
	}

	public void fireBroadCastMessage(String title, String message) {
		for (EventListener e : eventListeners) {
			e.broadcastMessageReceived(title, message);
		}
	}

	public void firePanicIncreased(String newinterval) {
		for (EventListener e : eventListeners) {
			e.panicIncreased(Integer.parseInt(newinterval));
		}
	}

	/** FIRE GAME GAMELISTENER EVENTS ***/

	public void fireTeamsReceived() {
		Log.i("UPDATE", "fireTeamsRecevied()");
		for (GameListener g : listeners) {
			g.teamsReceived();
		}
	}

	public void firePlayerJoined(Player p, Team team, Game game) {
		for (GameListener g : listeners) {
			g.playerJoined(p, team, game);
		}
	}

	public void firePlayerLeft(Player p) {
		for (GameListener g : listeners) {
			g.playerLeft(p);
		}
	}

	public void fireConnected() {
		for (GameListener g : listeners) {
			g.connected();
		}
	}

	public void fireDisconnected() {
		for (GameListener g : listeners) {
			g.disconnected();
		}
	}

	public void fireGameOver(String score) {
		for (GameListener g : listeners) {
			g.gameOver(score);
		}
	}

	public void fireGameStarted(String panicInterval) {
		for (GameListener g : listeners) {
			g.gameStarted(Integer.parseInt(panicInterval));
		}
	}

	public void fireYourTurn(String roundtime) {
		for (GameListener g : listeners) {
			g.yourTurn(Integer.parseInt(roundtime));
		}
	}

	public void addListener(GameListener listener) {
		listeners.add(listener);
	}

	public void removeListener(GameListener listener) {
		listeners.remove(listener);
	}

	public void addEventListener(EventListener listener) {
		eventListeners.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		eventListeners.remove(listener);
	}

}
