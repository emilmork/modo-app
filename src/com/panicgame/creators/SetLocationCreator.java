package com.panicgame.creators;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.panicgame.core.ApplicationObject;
import com.panicgame.core.R;
import com.panicgame.core.R.id;
import com.panicgame.core.R.layout;
import com.panicgame.listeners.MapListener;
import com.panicgame.models.Civilian;
import com.panicgame.models.Map;
import com.panicgame.models.Sector;
import com.panicgame.utils.Util;

public class SetLocationCreator {
	private Context context;
	private Activity a;
	private ApplicationObject app;
	private static SetLocationCreator setLocationCreator;
	public static AlertDialog dialog = null;
	public ArrayList<MapListener>listeners;


	private SetLocationCreator(Activity a) {
		this.context = a.getApplicationContext();
		this.a = a;
		app = (ApplicationObject) context.getApplicationContext();
		listeners = new ArrayList<MapListener>();
	}
	
	
	public void addMapListener(MapListener listener){
		listeners.add(listener);
	}
	public void removeMapListener(MapListener listener){
		listeners.remove(listener);
		setLocationCreator = null;
	}

	public static SetLocationCreator getInstance(Activity a) {
		if (setLocationCreator == null) {
			setLocationCreator = new SetLocationCreator(a);
			return setLocationCreator;
		} else {
			return setLocationCreator;
		}
	}
	
	public OnClickListener sectorClicked = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			for(MapListener l : listeners){
				l.checkedInAtSector((Integer)v.getTag());	
			}
			dialog.cancel();
		}
	};


	public void showSetLocationDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		LayoutInflater inflater = a.getLayoutInflater();
		View v = inflater.inflate(R.layout.setlocation_layout, null);
		
		TableLayout table_layout = (TableLayout)v.findViewById(R.id.location_table);
		TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage("Enter sector number");
		builder.setTitle("Check inn");
		
		ArrayList<ArrayList<Sector>> random_sectors  = app.map.getSectors();
		
		
		for(ArrayList<Sector> sectors : app.map.getSectors()){
			TableRow row = new TableRow(context);
			
			for(Sector s : sectors){
				Button b = new Button(context);
				b.setOnClickListener(sectorClicked);
				b.setTag(s.getId());
				TableRow.LayoutParams button_params = new TableRow.LayoutParams(android.widget.TableRow.LayoutParams.MATCH_PARENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT);
				b.setTextColor(Color.BLACK);
				b.setText(""+s.getId());
				row.addView(b,button_params);
			}
			table_layout.addView(row,row_params);
		}
		
		builder.setView(v).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	
            }
        });   
		
		// 3. Get the AlertDialog from create()
		dialog = builder.create();
		dialog.show();
	}
	
}
