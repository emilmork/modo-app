package com.panicgame.creators;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.panicgame.core.ApplicationObject;
import com.panicgame.core.R;
import com.panicgame.core.R.drawable;
import com.panicgame.models.Civilian;
import com.panicgame.models.Player;
import com.panicgame.models.Sector;
import com.panicgame.utils.Util;

public class MapLayoutCreator {
	private Sector sector;
	private Context context;
	public RelativeLayout sectorLayout;
	public LinearLayout players;
	private ApplicationObject app;

	public MapLayoutCreator(Sector sector, Context context) {
		this.sector = sector;
		this.context = context;
		app = (ApplicationObject) context.getApplicationContext();
		sectorLayout = new RelativeLayout(context, null, android.R.style.Widget_Button);
		players = new LinearLayout(context);

		sectorLayout.setBackgroundResource(R.drawable.map_sector_background);
		sectorLayout.setClickable(true);

		if (app.me.isSectorExplored(sector.getId())) {
			if (sector.isSafe()) {
				sectorLayout.setBackgroundResource(R.drawable.safe_sector);
			} else {

				int panicLevel = sector.getPaniclevel();

				switch (panicLevel) {
				case Civilian.OK:
					sectorLayout.setBackgroundResource(R.drawable.sector_green);
					break;
				case Civilian.MEDIUM:
					sectorLayout.setBackgroundResource(R.drawable.sector_orange);
					break;
				case Civilian.BAD:
					sectorLayout.setBackgroundResource(R.drawable.sector_red);
					break;
				case Civilian.DEAD:
					sectorLayout.setBackgroundResource(R.drawable.sector_black);
					break;
				default:
					sectorLayout.setBackgroundResource(R.drawable.sector_green);
					break;
				}
			}

		}

		/**
		 * Relative layout params
		 */
		TableRow.LayoutParams relative_params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
		relative_params.leftMargin = 2;
		relative_params.rightMargin = 2;
		relative_params.topMargin = 2;
		relative_params.bottomMargin = 2;

		sectorLayout.setLayoutParams(relative_params);

	}

	public RelativeLayout getLayout() {
		return sectorLayout;
	}

	public void addPlayers(ArrayList<Player> player_ids) {
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW, sector.getPanicLevelID());

		LinearLayout.LayoutParams player_image_params = new LinearLayout.LayoutParams(30, 45);
		LinearLayout playerList = new LinearLayout(context);

		TextView player = new TextView(context);
		player.setTextColor(Color.BLACK);
		ImageView player_image = new ImageView(context);
		

		if(app.me.getPostion_id()!=sector.getId()){
			for (Player p : player_ids) {
					String role = p.getRole();
					String image_name;
					image_name = role.substring(0, 1).toLowerCase() + role.substring(1);
					int image_id = app.getResources().getIdentifier(image_name, "drawable", "com.panicgame.core");
					
					if (player_ids.size() >= 2)
						player.setText(p.getName()+" + "+ (player_ids.size() - 1));
					else
						player.setText(p.getName());
					
					player_image.setBackgroundResource(image_id);
					playerList.addView(player_image, player_image_params);
					playerList.addView(player);
					sectorLayout.addView(playerList, params);
					break;
			}
			
			
			return;
		}
		for (Player p : player_ids) {
			if (p.getId().equals(app.me.getId())) {
				String role = p.getRole();
				String image_name;
				image_name = role.substring(0, 1).toLowerCase() + role.substring(1);
				int image_id = app.getResources().getIdentifier(image_name, "drawable", "com.panicgame.core");
				
				if (player_ids.size() >= 2)
					player.setText("You + " + (player_ids.size() - 1));
				else
					player.setText("You");
				
				player_image.setBackgroundResource(image_id);
				playerList.addView(player_image, player_image_params);
				playerList.addView(player);
				sectorLayout.addView(playerList, params);
			}
		}

	}

	/**
	 * @param type
	 * @return A Text for sector
	 */
	public void addTextView(int type) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		TextView tv = new TextView(context);
		tv.setTextColor(Color.BLACK);
		switch (type) {
		case Util.SECTOR_NAME:
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			tv.setLayoutParams(params);
			tv.setText(sector.getName());
			tv.setId(sector.getNameID());
			break;
		case Util.SECTOR_PANIC:
			params.addRule(RelativeLayout.BELOW, sector.getNameID());
			tv.setLayoutParams(params);
			tv.setText("Panic(" + sector.getPaniclevel() + ")");
			tv.setId(sector.getPanicLevelID());
			break;
		case Util.NUM_CIVIL:
			params.addRule(RelativeLayout.RIGHT_OF, sector.getCivilImageID());
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			tv.setLayoutParams(params);
			tv.setText("(" + sector.getCivils() + ")");
			tv.setId(sector.getCivilCountID());
			break;
		case Util.TEAM_MEMBER:
			params.addRule(RelativeLayout.RIGHT_OF, sector.getTeamMemberImageID());
			params.addRule(RelativeLayout.BELOW, sector.getPanicLevelID());
			tv.setLayoutParams(params);
			tv.setText("Team");
			break;
		case Util.ME:
			params.addRule(RelativeLayout.RIGHT_OF, sector.getMeImageID());
			params.addRule(RelativeLayout.BELOW, sector.getPanicLevelID());
			tv.setLayoutParams(params);
			tv.setText("You");
			break;
		}
		sectorLayout.addView(tv);
	}

	/**
	 * 
	 * @param type
	 * @param sector
	 * @return Image for sector
	 */
	public void addImage(int type) {
		// Image of you params
		ImageView img = new ImageView(context);
		switch (type) {
		case Util.ME:
			RelativeLayout.LayoutParams me_params = new RelativeLayout.LayoutParams(45, 55);
			me_params.addRule(RelativeLayout.BELOW, sector.getPanicLevelID());
			img.setBackgroundResource(R.drawable.player);
			img.setLayoutParams(me_params);
			img.setId(sector.getMeImageID());
			break;
		case Util.PERSON:
			RelativeLayout.LayoutParams civil_params = new RelativeLayout.LayoutParams(40, 40);
			civil_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			img.setBackgroundResource(R.drawable.civil);
			img.setLayoutParams(civil_params);
			img.setId(sector.getCivilImageID());
			break;
		case Util.TEAM_MEMBER:
			RelativeLayout.LayoutParams team_param = new RelativeLayout.LayoutParams(50, 50);
			team_param.addRule(RelativeLayout.BELOW, sector.getPanicLevelID());
			img.setBackgroundResource(R.drawable.crew_member);
			img.setLayoutParams(team_param);
			img.setId(sector.getTeamMemberImageID());
			break;
		case Util.DUMMY:
			RelativeLayout.LayoutParams dummy_param = new RelativeLayout.LayoutParams(50, 50);
			dummy_param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			img.setBackgroundResource(R.drawable.dummy);
			img.setLayoutParams(dummy_param);
			break;
		}
		sectorLayout.addView(img);
	}

}
