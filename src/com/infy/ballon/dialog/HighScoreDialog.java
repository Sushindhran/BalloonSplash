package com.infy.ballon.dialog;

import com.infy.balloon.R;
import com.infy.balloon.db.BalloonDataSource;
import com.infy.balloon.game.MainThread;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class HighScoreDialog extends Dialog {
	
	private BalloonDataSource dataSource;
	private String name;
	private EditText editText;
	private int isHighScore;
	private int gameScore;
	private MainThread thread;
	private Context context;
	
	public int getGameScore() {
		return gameScore;
	}
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}
	public int getIsHighScore() {
		return isHighScore;
	}
	public void setIsHighScore(int isHighScore) {
		this.isHighScore = isHighScore;
	}
	public MainThread getThread() {
		return thread;
	}
	public void setThread(MainThread thread) {
		this.thread = thread;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public BalloonDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(BalloonDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EditText getEditText() {
		return editText;
	}
	public void setEditText(EditText editText) {
		this.editText = editText;
	}

	public HighScoreDialog(Context context,BalloonDataSource dataSource,int score,MainThread thread,int gameScore) {
		super(context);
		// TODO Auto-generated constructor stub
		System.out.println("in constructor");
		this.context = context;
		this.dataSource = dataSource;
		this.isHighScore = score;
		this.gameScore = gameScore;
		this.thread = thread;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bestscore_screen);
		Button highScoreNameDialogButton = (Button)findViewById(R.id.saveHighScore);
		highScoreNameDialogButton.setOnClickListener(new OkClistener());
		editText = (EditText)findViewById(R.id.playerName);
	}
	
	private class OkClistener implements android.view.View.OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			name = editText.getText().toString();
			dataSource.open();
		/*	if(isHighScore==1){
				dataSource.insert(name,Integer.toString(gameScore, 10));
			}else{
				dataSource.updateHighScore(name,Integer.toString(gameScore, 10));
			}*/
			
			dataSource.insert(name,Integer.toString(gameScore, 10));
			
			dataSource.close();
			
			HighScoreDialog.this.dismiss();
			
			thread.setRunning(false);
			((Activity)context).finish();

		}
		
	}


}
