package com.panicgame.creators;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.panicgame.adapters.CivilianListAdapter;
import com.panicgame.adapters.EquipmentListAdapter;
import com.panicgame.adapters.HorizontalView;
import com.panicgame.adapters.PlayersInSectorAdapter;
import com.panicgame.core.ApplicationObject;
import com.panicgame.core.R;
import com.panicgame.core.R.id;
import com.panicgame.listeners.MapListener;
import com.panicgame.listeners.SectorListener;
import com.panicgame.models.Civilian;
import com.panicgame.models.Map;
import com.panicgame.models.Player;
import com.panicgame.models.Sector;
import com.panicgame.utils.Util;

public class MapCreator {
	private Context context;
	private Activity a;
	private ApplicationObject app;
	private ArrayList<MapListener> mapListeners;
	private static LinearLayout map_layout;
	private static LinearLayout event_layout_parent;
	private static MapCreator mapCreator;

	private static ListView civilian_list_view;
	//private static HorizontalView player_list_view;
	
	private static ListView equipment_list_view;

	private CivilianListAdapter civilian_adapter = null;
	//private PlayersInSectorAdapter players_adapter = null;
	private EquipmentListAdapter equipment_adapter = null;

	private TextView sector_name;

	private TextView event_name;
	private ImageView event_image;

	private RelativeLayout sector_layout;
	private LinearLayout sector_playerlist;

	private MapCreator(Activity a) {
		this.context = a.getApplicationContext();
		this.a = a;
		app = (ApplicationObject) context.getApplicationContext();
		mapListeners = new ArrayList<MapListener>();

		map_layout = (LinearLayout) ((Activity) a).findViewById(R.id.layout_map_table);
		sector_layout = (RelativeLayout) ((Activity) a).findViewById(R.id.sector_view);
		sector_name = (TextView) ((Activity) a).findViewById(R.id.sector_name);
		sector_playerlist = (LinearLayout)((Activity)a).findViewById(R.id.sector_bottom);

		event_layout_parent = (LinearLayout) ((Activity) a).findViewById(R.id.event_layout_super);
		event_name = (TextView) ((Activity) a).findViewById(R.id.event_message);
		event_image = (ImageView) ((Activity) a).findViewById(R.id.event_image);
		mapListeners.add((MapListener) a);

		civilian_list_view = (ListView) ((Activity) a).findViewById(R.id.civilian_listview);
		//player_list_view = (HorizontalView)((Activity) a).findViewById(R.id.players_listview);
		equipment_list_view = (ListView) ((Activity) a).findViewById(R.id.equipment_listview);
	}

	public void reset() {
		mapCreator = null;
	}

	public void removeListener(MapListener listener) {
		if (mapListeners != null)
			mapListeners.remove(listener);
		if (equipment_adapter != null)
			equipment_adapter.removeSectorListener();
	}

	public static MapCreator getInstance(Activity a) {
		if (mapCreator == null) {
			mapCreator = new MapCreator(a);
			return mapCreator;
		} else {
			return mapCreator;
		}
	}

	private void fireSectorClicked(int viewId) {
		for (MapListener listener : mapListeners) {
			listener.sectorClicked(viewId);
		}
	}

	public OnClickListener sectorListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			fireSectorClicked(v.getId());

		}
	};

	public void createRoom(Sector sector) {
		sector_name.setText(sector.getName());
		if (!sector.getEvent().equals("none")) {
			event_layout_parent.setVisibility(View.VISIBLE);
			sector_playerlist.setVisibility(View.INVISIBLE);
			if (civilian_adapter != null)
				civilian_adapter.clear();
			if (equipment_adapter != null)
				equipment_adapter.clear();
			// Sector has an event the player must fix
			String eventName = sector.getEvent();
			Log.i("Evnet", "Evnet name: " + eventName);
			int image_id = app.getResources().getIdentifier(eventName, "drawable", "com.panicgame.core");
			int text_id = app.getResources().getIdentifier("event_" + eventName, "string", "com.panicgame.core");

			event_name.setText(app.getString(text_id));
			event_image.setBackgroundResource(image_id);

			sector_layout.refreshDrawableState();
		} else {
			event_layout_parent.setVisibility(View.GONE);
			

			civilian_adapter = new CivilianListAdapter(sector.getCivilians(), app.getApplicationContext());
			civilian_list_view.setAdapter(civilian_adapter);
			civilian_list_view.setChoiceMode(2);

			createPlayerList(sector);

			equipment_adapter = new EquipmentListAdapter(sector.getEq(), app.getApplicationContext());
			equipment_adapter.addSectorListener((SectorListener) a);
			equipment_list_view.setAdapter(equipment_adapter);

		}
	}
	
	public void createPlayerList(Sector sector){
		sector_playerlist.setVisibility(View.VISIBLE);
		sector_playerlist.removeAllViews();

		
		for(Player p : sector.getPlayers()){
			Log.i("Players","Players in "+sector.getName() +" player: "+p.getName());
			LayoutInflater inflator = ((Activity)a).getLayoutInflater();
			View playerView = inflator.inflate(R.layout.players_listview, null);
						
			TextView player_name = (TextView)playerView.findViewById(R.id.player_name_list_view);
			player_name.setText(p.getName());
			
			String role = p.getRole();
			String image_name;
			image_name = role.substring(0, 1).toLowerCase() + role.substring(1);
			int image_id = app.getResources().getIdentifier(image_name, "drawable", "com.panicgame.core");
			ImageView player_figure = (ImageView)playerView.findViewById(R.id.player_image);
			player_figure.setBackgroundResource(image_id);
			
			sector_playerlist.addView(playerView);
		}
	}

	public void createMap() {
		Log.i("UPDATE", "CREATING MAP **");
		map_layout.removeAllViews();

		LinearLayout tl = (LinearLayout) ((Activity) a).findViewById(R.id.layout_map_table);

		if (app.sectors == null)
			return;

		for (ArrayList<Sector> s : app.map.getSectors()) {

			TableRow row = new TableRow(context);
			TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);

			for (Sector sector : s) {
				boolean explored = app.me.isSectorExplored(sector.getId());
				MapLayoutCreator mapCreator = new MapLayoutCreator(sector, context);
				mapCreator.getLayout().setId(sector.getId());
				mapCreator.getLayout().setOnClickListener(sectorListener);

				// Set sector name
				mapCreator.addTextView(Util.SECTOR_NAME);
				// Set sector panic value
				if (explored) {
					mapCreator.addTextView(Util.SECTOR_PANIC);
					//Add players
					
					System.out.println("Sector content on render:");
					System.out.println(sector);
					mapCreator.addPlayers(sector.getPlayers());
					
					mapCreator.addImage(Util.PERSON);
					mapCreator.addTextView(Util.NUM_CIVIL);
				}else{
					mapCreator.addImage(Util.DUMMY);
				}


				row.addView(mapCreator.getLayout());
			}

			tl.addView(row, row_params);
		}
		Log.i("MapCreator", "Done creating map. Refreshing view");
		tl.refreshDrawableState();
		map_layout.refreshDrawableState();

	}
}
