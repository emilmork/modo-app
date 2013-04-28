package com.panicgame.core;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends SuperActivity {
	private TextView message,summary;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over_layout);
		
		
		message = (TextView)findViewById(R.id.game_over_message);
		summary = (TextView)findViewById(R.id.summary_text);
		
		String score = getIntent().getStringExtra("score");
		if(score!=null){
			message.setText(score+"/1000");
		}
		
		int score_value = Integer.parseInt(score);
		
		
		if(score_value >= 800){
			//Good score
			summary.setText("Keep up the good work!");
			
		}else if(score_value >= 500 && score_value < 800){
			//Medium score
			summary.setText("This was okey, but better luck next time");
			
		}else if(score_value < 500 && score_value > 200){
			//Low score
			summary.setText("Not very good. Your team saved less then half of the civilians.");
			
		}else{
			summary.setText("That's terrible.");
		}
	}
	
	
	
	
	public void onClick(View view){
		this.finish();
	}
	
	
	public void onBackPressed(){
		super.onBackPressed();
	}

}
