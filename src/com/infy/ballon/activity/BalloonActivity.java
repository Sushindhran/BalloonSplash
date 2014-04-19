package com.infy.ballon.activity;

import com.infy.balloon.game.MainGamePanel;
import com.infy.balloon.game.MainThread;
import com.infy.balloon.game.MainGamePanel.CountdownTimer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class BalloonActivity extends Activity implements SensorEventListener {
	private SensorManager sm;
	float accelValues[];
	private float y;
	private float x;
	private static final String TAG = BalloonActivity.class.getSimpleName();
	private MainGamePanel view;
	private static MainThread gameThread;
	private CountdownTimer timer;
	private final long startTime = 61000;
	private final long interval = 1000;
	private Context context;

	public CountdownTimer getTimer() {
		return timer;
	}
	public void setTimer(CountdownTimer timer) {
		this.timer = timer;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public long getStartTime() {
		return startTime;
	}
	public long getInterval() {
		return interval;
	}
	public SensorManager getSm() {
		return sm;
	}
	public void setSm(SensorManager sm) {
		this.sm = sm;
	}
	public float[] getAccelValues() {
		return accelValues;
	}
	public void setAccelValues(float[] accelValues) {
		this.accelValues = accelValues;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public MainGamePanel getView() {
		return view;
	}
	public void setView(MainGamePanel view) {
		this.view = view;
	}
	public static MainThread getGameThread() {
		return gameThread;
	}
	public static void setGameThread(MainThread gameThread) {
		BalloonActivity.gameThread = gameThread;
	}
	public static String getTag() {
		return TAG;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//requesting to turn title off
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set MainGamePanel as View
		context = this;
		view = new MainGamePanel(this);
		setContentView(view);
		timer = view.new CountdownTimer(startTime, interval);
		timer.start();

		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	protected void onResume(){
		Log.d(TAG, "Resuming!");
		super.onResume();
		//gameThread.resume();
		Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause(){
		Log.d(TAG, "Pausing the Game!");
		super.onPause();
		gameThread.setRunning(false);
		((Activity)getContext()).finish();
	}
	
	@Override
	protected void onDestroy(){
		Log.d(TAG, "Destroying!");
		super.onDestroy();
	}

	@Override
	protected void onStop(){
		Log.d(TAG,"Stopping!");
		sm.unregisterListener(this);
		super.onStop();
		/*try {
			gameThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		// check for correct sensor....redundant here because we are using only
		// one sensor
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			// event.values is a float array of acceleration in three direction
			// i.e. x,y and z
			accelValues = event.values;

			x = accelValues[0];
			y = accelValues[1];			

			// setting center of the circle based on accelerometer values
			view.setX(view.getX()-2*x);			
			//archerView.setY(archerView.getY() + 2 * y);

			// remember to invalidate the view every time we want it to be
			// redrawn
			view.invalidate();

		}

	}
}
