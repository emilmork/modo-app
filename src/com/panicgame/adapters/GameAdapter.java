package com.panicgame.adapters;
import java.util.ArrayList;
import com.panicgame.core.R;
import com.panicgame.models.Game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameAdapter extends ArrayAdapter<Game> {

	private Context context;
	private ArrayList<Game> values;
	
	public GameAdapter(Context context, ArrayList<Game> values) {
		super(context, com.panicgame.core.R.layout.games_rowlayout, values);
		this.context = context;
		this.values = values;
	}
	@Override
	public int getCount(){
		return values.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(com.panicgame.core.R.layout.games_rowlayout, parent, false);
		
		TextView gameName =(TextView)rowView.findViewById(R.id.game_name);
		gameName.setText(values.get(position).getName());
		TextView gameDesc =(TextView)rowView.findViewById(R.id.game_desc);
		gameDesc.setText(values.get(position).getDesc());
	    
	    return rowView;
	}

}

