package com.panicgame.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.panicgame.core.ApplicationObject;
import com.panicgame.core.R;
import com.panicgame.core.R.drawable;
import com.panicgame.core.R.id;
import com.panicgame.core.R.layout;
import com.panicgame.listeners.SectorListener;
import com.panicgame.models.Civilian;

public class EquipmentListAdapter extends ArrayAdapter<Integer> {
	 
	private ArrayList<Integer> equipment;
	private Context context;
	private ApplicationObject app;
	private SectorListener sectorListener;
	 
	public EquipmentListAdapter(ArrayList<Integer> equipment, Context ctx) {
	    super(ctx, R.layout.equipment_layout_row, equipment);
	    this.equipment = equipment;
	    this.context = ctx;
	    app = (ApplicationObject)context.getApplicationContext();
	}
	
	public void addSectorListener(SectorListener listener){
		this.sectorListener = listener;
	}
	public void removeSectorListener(){
		this.sectorListener = null;
	}
	 
	public View getView(int position, View convertView, ViewGroup parent) {
	     
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.equipment_layout_row, parent, false);
	    }
	    	//Type of equipment
	    	int pos = position;
	    	//How many of that equipment
	    	int id = equipment.get(pos);
	    	
	    	TextView equipment_text = (TextView)convertView.findViewById(R.id.equipment_name);
	    	ImageView equipment_image = (ImageView)convertView.findViewById(R.id.available_equipment_image);
	    	Button pickup_btn = (Button)convertView.findViewById(R.id.equipment_pickup_btn);
	    	RelativeLayout row = (RelativeLayout)convertView.findViewById(R.id.equipment_row);
	    	
	    	if(id == 1){
	    		equipment_image.setBackgroundResource(R.drawable.equipment_extinguisher);
	    		equipment_text.setText("Extinguisher");
	    	}else if(id == 2){
	    		equipment_image.setBackgroundResource(R.drawable.equipment_hammer);
	    		equipment_text.setText("Hammer");
	    	}
	    	
	    	pickup_btn.setTag(id);
	    	pickup_btn.setOnClickListener(btn_listener);
	     
	     
	    return convertView;
	}
	
	private OnClickListener btn_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			sectorListener.equipmentPickedUp((Integer)v.getTag());
		}
	};


}