package com.panicgame.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.panicgame.core.ApplicationObject;
import com.panicgame.core.R;
import com.panicgame.core.R.drawable;
import com.panicgame.core.R.id;
import com.panicgame.core.R.layout;
import com.panicgame.models.Civilian;

public class CivilianListAdapter extends ArrayAdapter<Civilian> {
	 
	private ArrayList<Civilian> civilians;
	private Context context;
	private ApplicationObject app;
	 
	public CivilianListAdapter(ArrayList<Civilian> planetList, Context ctx) {
	    super(ctx, R.layout.civilian_row_layout, planetList);
	    this.civilians = planetList;
	    this.context = ctx;
	    app = (ApplicationObject)context.getApplicationContext();
	}
	 
	public View getView(int position, View convertView, ViewGroup parent) {
	     
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.civilian_row_layout, parent, false);
	    }
	    	Civilian civilian = civilians.get(position);
	    	
	    	
			int box_background_id = context.getResources().getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
			CheckBox box = (CheckBox) convertView.findViewById(R.id.civilian_checkbox);
			box.setOnCheckedChangeListener(civil_checked);
			box.setTag(civilian.getId());
			box.setButtonDrawable(box_background_id);
			
		    
	        TextView health = (TextView)convertView.findViewById(R.id.civilan_health);
	        TextView panic = (TextView)convertView.findViewById(R.id.civilan_panic);
	        TextView name = (TextView)convertView.findViewById(R.id.civilian_name);
	        
	        ImageView health_image = (ImageView)convertView.findViewById(R.id.civilian_health_image);
	        ImageView panic_image = (ImageView)convertView.findViewById(R.id.civilian_panic_image);
	        
	        health.setText(civilian.getHealth()+" %");
	        panic.setText(""+civilian.getPanic()+"/10");
	        name.setText(civilian.getName());
	    
	    	RelativeLayout row = (RelativeLayout)convertView.findViewById(R.id.civilian_row);
	    	
			box.setVisibility(View.VISIBLE);
			health.setVisibility(View.VISIBLE);
			panic.setVisibility(View.VISIBLE);
			health_image.setVisibility(View.VISIBLE);
			panic_image.setVisibility(View.VISIBLE);
			name.setTextColor(Color.BLACK);
	    	
	    	switch (civilian.getCivilianStatus()) {
			case Civilian.OK:
				row.setBackgroundResource(R.drawable.sector_green);
				break;
			case Civilian.MEDIUM:
				row.setBackgroundResource(R.drawable.sector_orange);
				break;
			case Civilian.BAD:
				row.setBackgroundResource(R.drawable.sector_red);
				break;
			case Civilian.DEAD:
				row.setBackgroundResource(R.drawable.sector_black);
				
				name.setText(civilian.getName()+"(Dead)");
				name.setTextColor(Color.WHITE);
				box.setVisibility(View.INVISIBLE);
				health.setVisibility(View.INVISIBLE);
				panic.setVisibility(View.INVISIBLE);
				health_image.setVisibility(View.INVISIBLE);
				panic_image.setVisibility(View.INVISIBLE);
				break;
			}  
	     
	    return convertView;
	}
	
	
	private OnCheckedChangeListener civil_checked = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			Log.i("Checked"," Checkbox pressed: " + isChecked);
			if(isChecked){
				boolean hasCapasety = app.me.civilianChecked((Integer)buttonView.getTag());
				if(!hasCapasety){
					buttonView.setChecked(false);
				}
				
			}else{
				app.me.civilianNotChecked((Integer)buttonView.getTag());
			}			
		}
	};


}