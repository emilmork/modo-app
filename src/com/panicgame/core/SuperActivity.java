package com.panicgame.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.*;
import com.panicgame.actions.PlayerLeave;
import com.panicgame.managers.InputManager;
import com.panicgame.models.Player;
import com.panicgame.models.ProgressObject;
import com.panicgame.models.Sector;
import com.panicgame.utils.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint.Join;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SuperActivity extends SherlockActivity {
	public ApplicationObject app;
	public Gson gson;
	public Handler h;
	public ProgressDialog progressdialog;
	public Vibrator v;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		h = new Handler();
		app = (ApplicationObject) getApplicationContext();
		gson = new Gson();
		progressdialog = new ProgressDialog(this);
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onResume() {
		super.onResume();
		if (app == null) {
			app = (ApplicationObject) getApplicationContext();
		}
		if (gson == null) {
			gson = new Gson();
		}
	}
	
	public int getLayoutIdFromName(String name,String quantifyer){
		String layout_name = name.substring(0, 1).toLowerCase() + name.substring(1);
		int image_id = app.getResources().getIdentifier(layout_name, quantifyer, "com.panicgame.core");
		return image_id;
	}

	public String fixNameForSpace(String name) {
		return name.replace(" ", "%20");
	}

	public void showProgressDialog(String message) {
		progressdialog = ProgressDialog.show(this, "", message);
	}

	public void stopProgressDialog() {
		if (progressdialog != null && progressdialog.isShowing()) {
			progressdialog.dismiss();
		}
	}

	public void showToast(final String message) {
		h.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onDestroy() {
		super.onDestroy();
		try {
			// app.network.destroySocket();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void vibrate(int duration) {
		v.vibrate(duration);
	}

	public void showDialog(final String title, final String message) {
		h.post(new Runnable() {

			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(SuperActivity.this);
				builder.setTitle(title);
				if (title == "Message") {
					builder.setIcon(R.drawable.walkie_talkie);
				}
				builder.setMessage(message).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				builder.create().show();

			}
		});

	}

}
