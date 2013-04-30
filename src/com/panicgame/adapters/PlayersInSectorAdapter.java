package com.panicgame.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.panicgame.core.ApplicationObject;
import com.panicgame.core.R;
import com.panicgame.models.Player;

public class PlayersInSectorAdapter extends ArrayAdapter<Player> {
	private ArrayList<Player> players;
	private Context context;
	private ApplicationObject app;

	public PlayersInSectorAdapter(ArrayList<Player> players, Context ctx) {
		super(ctx, R.layout.players_listview, players);
		this.players = players;
		this.context = ctx;
		app = (ApplicationObject) context.getApplicationContext();
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// First let's verify the convertView is not null
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.players_listview, parent, false);
		}
		Player player = players.get(position);
		
		TextView t = (TextView)convertView.findViewById(R.id.player_name_list_view);
		t.setText(player.getName());
		
		

		return convertView;

	}
}
