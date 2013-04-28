package com.panicgame.core;

import com.panicgame.listeners.GameListener;
import com.panicgame.managers.InputManager;
import com.panicgame.models.Game;
import com.panicgame.models.Player;
import com.panicgame.models.Team;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowDebreaf extends SuperActivity implements GameListener {

	TextView debreaf_text;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_debreaf);
		
		
		String message = getIntent().getStringExtra("debrief");
		debreaf_text = (TextView)findViewById(R.id.debreaf_message);
		if(message!=null){
			debreaf_text.setText(message);
		}
		
		InputManager.getInstance(this).addListener(this);
	
	}
	
	
	
	public void onClick(View view){
		this.finish();
	}
	
	
	public void onBackPressed(){
		super.onBackPressed();
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
		InputManager.getInstance(ShowDebreaf.this).removeListener(ShowDebreaf.this);
		this.finish();
	}



	@Override
	public void gameOver(String score) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void teamsReceived() {
		// TODO Auto-generated method stub
		
	}
}
