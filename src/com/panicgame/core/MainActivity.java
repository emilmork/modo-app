package com.panicgame.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.reflect.TypeToken;
import com.panicgame.actions.Action;
import com.panicgame.actions.GetTeams;
import com.panicgame.listeners.GameListener;
import com.panicgame.managers.InputManager;
import com.panicgame.models.Game;
import com.panicgame.models.Player;
import com.panicgame.models.Team;
import com.panicgame.popupwindow.ActionItem;
import com.panicgame.popupwindow.QuickAction;
import com.panicgame.roles.Firefighter;
import com.panicgame.roles.Medic;
import com.panicgame.roles.Military;
import com.panicgame.roles.Volunteer;
import com.panicgame.utils.Util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SuperActivity implements GameListener {

	private Spinner spinner;
	private Spinner role_spinner;
	private EditText inputField;
	private Button findGamesBtn;
	private Button reconnectButton;
	private ArrayAdapter<String> spinner_adapter;
	private ArrayAdapter<String> role_adapter;
	
	private Handler h;
	private TextView link;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	private int attemtedConnectes = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showProgressDialog("Connecting..");

		h = new Handler();
		inputField = (EditText) findViewById(R.id.name_input);
		findGamesBtn = (Button) findViewById(R.id.log_inn_button);
		reconnectButton = (Button) findViewById(R.id.reconnect_btn);
		findGamesBtn.setEnabled(false);

		
		InputManager.getInstance(this).addListener(this);

		app.client = new TCPClient(this);
		app.client.start();
		
		
		link =(TextView)findViewById(R.id.about_link);
		link.setClickable(true);
		link.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='http://gribb.dyndns.org:3010/about'> New to MoDo? Click here for a fast intro. </a>";

		link.setText(Html.fromHtml(text));
		
		pref = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		editor = pref.edit();
		
		String name = pref.getString("name", "");
		if(!name.isEmpty()){
			inputField.setText(name);
		}
		
		initRoleSpinner();
		
		

	}
	

	
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuinflater = getSupportMenuInflater();
		menuinflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.refresh:
			showProgressDialog("Refreshing teams..");
			app.client.write(new GetTeams().getAction());
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onStop() {
		super.onStop();
		stopProgressDialog();
	}
	

	public void onClick(View view) {
		// equipmentAction.show(view);

		if (view.getId() == R.id.log_inn_button) {
			String name = inputField.getText().toString().trim();
			if (!name.equals("")) {
				
				editor.putString("name", name);
				editor.commit();
				
				int pos = role_spinner.getSelectedItemPosition();
				String role = role_adapter.getItem(pos);
				
				Player player;
				if(role.equals("Medic")){
					player = new Medic(name);
				}else if(role.equals("Firefighter")){
					player = new Firefighter(name);
				}else if(role.equals("Volunteer")){
					player = new Volunteer(name);
				}else{
					player = new Military(name);
				}
				app.me = player;
				app.me.setRole(role);
				app.me.initEquipment(MainActivity.this);
				
				
				/*
				app.me = new Player(name);
				app.me.addEquipment(Util.ID_EXTINGUISHER);
				app.me.addEquipment(Util.ID_HAMMER);
				app.me.addEquipment(Util.ID_MEDIC);
				*/
				Intent i = new Intent(getApplicationContext(), GameListActivity.class);
				i.putExtra(Util.TEAM_POSITION, spinner.getSelectedItemPosition());
				startActivityForResult(i, 1);
			}else{
				showToast("You must enter a username");
			}
		} else if (view.getId() == R.id.reconnect_btn) {
			h.post(new Runnable() {

				@Override
				public void run() {
					showProgressDialog("Connecting..");
					app.client = null;
					app.client = new TCPClient(MainActivity.this);
					app.client.start();

				}
			});

		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		showProgressDialog("Refreshing teams..");
		app.client.write(new GetTeams().getAction());
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Exit");
		builder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				try {

					InputManager.getInstance(getApplicationContext()).removeListener(MainActivity.this);
					/*
					 * app.network.cancel(true); app.network.socket.close();
					 * app.network.inputstream.close();
					 * app.network.dataOutput.close();
					 */
					app.client.interrupt();
					app.client.cancel = true;

				} catch (Exception e) {
					e.printStackTrace();
				}
				finish();
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
	
	
	private void initRoleSpinner(){
		ArrayList<String>roles = new ArrayList<String>();
		roles.add("Volunteer");
		roles.add("Firefighter");
		roles.add("Medic");
		roles.add("Militery");
		
		role_adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,roles);
		role_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		role_spinner = (Spinner)findViewById(R.id.role_spinner);
		role_spinner.setAdapter(role_adapter);
				
	}


	private void initSpinner() {
		h.post(new Runnable() {
			@Override
			public void run() {
				ArrayList<String> teams = new ArrayList<String>();
				for (Team t : app.teams)
					teams.add(t.name);
				spinner = (Spinner) findViewById(R.id.team_spinner);
				spinner_adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, teams);
				spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(spinner_adapter);

				findGamesBtn.setEnabled(true);
				stopProgressDialog();
			}
		});
	}

	@Override
	public void connected() {
		h.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				reconnectButton.setVisibility(View.INVISIBLE);
				showToast("Connected");
			}
		});
		
		Action action = new GetTeams();
		app.client.write(action.getAction());
	}

	@Override
	public void playerJoined(Player player, Team team, Game game) {
		showToast(player.getName() + " joined " + game.getName());
	}

	@Override
	public void playerLeft(Player player) {
		showToast(player.getName() + "left a game");
	}

	@Override
	public void disconnected() {
		stopProgressDialog();
		if (attemtedConnectes < 3) {
			h.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					app.client = null;
					app.client = new TCPClient(MainActivity.this);
					app.client.start();
					attemtedConnectes++;
				}
			});

		} else {
			h.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					reconnectButton.setVisibility(View.VISIBLE);
					showToast("Could not connect to server");
				}
			});

		}

	}

	@Override
	public void teamsReceived() {
		showToast("Teams and games received");
		initSpinner();
		

	}

	@Override
	public void yourTurn(int roundtime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameStarted(int panicinterval) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameOver(String score) {
		// TODO Auto-generated method stub
		
	}

}
