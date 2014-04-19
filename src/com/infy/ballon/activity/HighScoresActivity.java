package com.infy.ballon.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.infy.balloon.R;
import com.infy.balloon.db.BalloonDataSource;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoresActivity extends Activity {
	private BalloonDataSource dataSource;
	private TextView tableView[][]=new TextView[5][3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore_screen);
		tableView[0][0] = (TextView) findViewById(R.id.rowName1);
		tableView[0][1] = (TextView) findViewById(R.id.rowScore1);
		tableView[0][2] = (TextView) findViewById(R.id.rowDate1);

		tableView[1][0] = (TextView) findViewById(R.id.rowName2);
		tableView[1][1] = (TextView) findViewById(R.id.rowScore2);
		tableView[1][2] =  (TextView) findViewById(R.id.rowDate2);

		tableView[2][0] = (TextView) findViewById(R.id.rowName3);
		tableView[2][1] = (TextView) findViewById(R.id.rowScore3);
		tableView[2][2] = (TextView) findViewById(R.id.rowDate3);

		tableView[3][0] = (TextView) findViewById(R.id.rowName4);
		tableView[3][1] = (TextView) findViewById(R.id.rowScore4);
		tableView[3][2] = (TextView) findViewById(R.id.rowDate4);

		tableView[4][0] = (TextView) findViewById(R.id.rowName5);
		tableView[4][1] = (TextView) findViewById(R.id.rowScore5);
		tableView[4][2] = (TextView) findViewById(R.id.rowDate5);

		dataSource = new BalloonDataSource(this);
		getHighScores();	
	}

	public void getHighScores(){
		dataSource.open();
		Cursor cursor = dataSource.queryAll();
		cursor.moveToFirst();
		if(cursor.getCount()==0){
			tableView[2][1].setText("No High Scores have been made!");
		}
		else{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
			Calendar calendar = Calendar.getInstance();
			for(int i=0;i<cursor.getCount();i++){
				tableView[i][0].setText(cursor.getString(1));
				tableView[i][1].setText(cursor.getString(2));
				try {
					Date scoreDate = dateFormat.parse(cursor.getString(3));
					calendar.setTime(scoreDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tableView[i][2].setText(dateFormat.format(calendar.getTime()));
				cursor.moveToNext();
			}
		}
		dataSource.close();
	}
}
