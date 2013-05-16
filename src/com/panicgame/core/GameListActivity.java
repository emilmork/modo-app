package com.panicgame.core;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.google.gson.reflect.TypeToken;
import com.panicgame.adapters.GameAdapter;
import com.panicgame.listeners.GameListener;
import com.panicgame.models.Game;
import com.panicgame.models.Player;
import com.panicgame.models.Team;
import com.panicgame.utils.Util;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GameListActivity extends SuperActivity implements OnItemClickListener,GameListener {
	private ArrayList<Game> games;
	private ListView listview;
	private GameAdapter game_adapter;
	private int team_selected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.games_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		

	}
	
	public void onResume(){
		super.onResume();
		init();
	}
	
	
	public void joineGame(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Joine game");
		builder.setMessage("Are you sure you want to join this game ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		builder.create().show();
	}
	
	private void init(){
		games = new ArrayList<Game>();
		games.clear();
		
		team_selected = (int) getIntent().getIntExtra(Util.TEAM_POSITION,-1);
		if (team_selected >= 0) {
			games = app.teams.get(team_selected).getGames();
			if (games.isEmpty()) {
				Toast.makeText(this, "No games for the moment",Toast.LENGTH_LONG).show();
				this.finish();
			} else {
				getSupportActionBar().setSubtitle("Games for " + app.teams.get(team_selected).getName());
				initList(games);
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		this.finish();
		
	}

	private void initList(final ArrayList<Game> games) {
		listview = (ListView) findViewById(R.id.listview_games);
		game_adapter = new GameAdapter(this, games);
		listview.setAdapter(game_adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1,final int position, long arg3) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Joine game");
		builder.setMessage("Are you sure you want to joine this game ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				
				Intent i = new Intent(getApplicationContext(),GameActivity.class);
				i.putExtra(Util.GAME_POSITION, position);
				i.putExtra(Util.TEAM_POSITION, team_selected);
				startActivityForResult(i,1);
				
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		builder.create().show();
		
	}

	@Override
	public void connected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerJoined(Player player, Team team, Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerLeft(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void yourTurn(int roundtime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStarted(int panicintercal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameOver(String score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teamsReceived() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void negotiationStarted(int timer) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	


}
