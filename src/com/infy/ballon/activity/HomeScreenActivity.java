package com.infy.ballon.activity;

import com.infy.balloon.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class HomeScreenActivity extends Activity {
	private Intent intent;
	private TextView textView;
	//MainGamePanel mainGamePanel = new MainGamePanel(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		textView = (TextView) findViewById(R.id.start);
		textView.setTextColor(Color.WHITE);
		textView = (TextView) findViewById(R.id.rules);
		textView.setTextColor(Color.WHITE);
		textView = (TextView) findViewById(R.id.highScores);
		textView.setTextColor(Color.WHITE);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		textView = (TextView) findViewById(R.id.start);
		textView.setTextColor(Color.WHITE);
		textView = (TextView) findViewById(R.id.rules);
		textView.setTextColor(Color.WHITE);
		textView = (TextView) findViewById(R.id.highScores);
		textView.setTextColor(Color.WHITE);
	}

	public void onClick(View v) {
		textView = (TextView) findViewById(v.getId());
		textView.setTextColor(Color.DKGRAY);
		if(v.getId()==R.id.start){			
			intent = new Intent(HomeScreenActivity.this, BalloonActivity.class);			
			startActivity(intent);
		}
		else if(v.getId()==R.id.rules){
			intent = new Intent(HomeScreenActivity.this, RulesActivity.class);			
			startActivity(intent);
		}
		else if(v.getId()==R.id.highScores){
			intent = new Intent(HomeScreenActivity.this, HighScoresActivity.class);			
			startActivity(intent);
		}
		else{
			//Do nothing
		}
	}
}
