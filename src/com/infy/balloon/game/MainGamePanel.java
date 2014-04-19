package com.infy.balloon.game;

import com.infy.ballon.activity.BalloonActivity;
import com.infy.ballon.dialog.HighScoreDialog;
import com.infy.balloon.R;
import com.infy.balloon.db.BalloonDataSource;
import com.infy.balloon.draw.Arrow;
import com.infy.balloon.draw.Balloon;
import com.infy.balloon.draw.Bullet;
import com.infy.balloon.draw.Speed;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = MainGamePanel.class.getSimpleName();
	private static final int TOP=0;
	private static final int RIGHT=1;
	private static final int BOTTOM=2;
	private static final int LEFT=3;
	private static final int ABSCISSA=0;
	private static final int ORDINATE=1;
	private MainThread thread;
	private Balloon droid;
	private final int imageCount = 6;
	private Bitmap[] picArray = new Bitmap[imageCount];
	private Drawable archer;
	private Drawable grassTile;
	private Drawable pebble;
	private Drawable blackTile;
	private Display display;
	private float x,y;
	private int screenWidth,screenHeight;
	private Arrow arrow;
	private AssetManager asset;
	private Bitmap arrowImage;
	private Paint textPaint = new Paint();
	private Paint rectPaint = new Paint();
	private int gameScore;
	private Context context;
	private TextView text;
	private TextView timeElapsedView;
	private RelativeLayout baseLayout;
	private BalloonDataSource dataSource;

	private SoundPool soundPool;
	private int soundId;
	private boolean soundLoaded = false;
	
	private TextView timeElapsedText;
	private TextView scoreTextView;
	private TextView scoreView;

	
	public TextView getScoreView() {
		return scoreView;
	}
	public void setScoreView(TextView scoreView) {
		this.scoreView = scoreView;
	}
	public TextView getScoreTextView() {
		return scoreTextView;
	}
	public void setScoreTextView(TextView scoreTextView) {
		this.scoreTextView = scoreTextView;
	}
	public TextView getTimeElapsedView() {
		return timeElapsedView;
	}
	public void setTimeElapsedView(TextView timeElapsedView) {
		this.timeElapsedView = timeElapsedView;
	}
	public TextView getTimeElapsedText() {
		return timeElapsedText;
	}
	public void setTimeElapsedText(TextView timeElapsedText) {
		this.timeElapsedText = timeElapsedText;
	}
	public SoundPool getSoundPool() {
		return soundPool;
	}
	public void setSoundPool(SoundPool soundPool) {
		this.soundPool = soundPool;
	}
	public int getSoundId() {
		return soundId;
	}
	public void setSoundId(int soundId) {
		this.soundId = soundId;
	}
	public boolean isSoundLoaded() {
		return soundLoaded;
	}
	public void setSoundLoaded(boolean soundLoaded) {
		this.soundLoaded = soundLoaded;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public Drawable getBlackTile() {
		return blackTile;
	}
	public void setBlackTile(Drawable blackTile) {
		this.blackTile = blackTile;
	}
	public AssetManager getAsset() {
		return asset;
	}
	public void setAsset(AssetManager asset) {
		this.asset = asset;
	}
	public Drawable getGrassTile() {
		return grassTile;
	}
	public void setGrassTile(Drawable grassTile) {
		this.grassTile = grassTile;
	}
	public Drawable getPebble() {
		return pebble;
	}
	public void setPebble(Drawable pebble) {
		this.pebble = pebble;
	}
	public Bitmap getArrowImage() {
		return arrowImage;
	}
	public void setArrowImage(Bitmap arrowImage) {
		this.arrowImage = arrowImage;
	}
	public Arrow getArrow() {
		return arrow;
	}
	public void setArrow(Arrow arrow) {
		this.arrow = arrow;
	}
	public MainThread getThread() {
		return thread;
	}
	public void setThread(MainThread thread) {
		this.thread = thread;
	}
	public Balloon getDroid() {
		return droid;
	}
	public void setDroid(Balloon droid) {
		this.droid = droid;
	}
	public Bitmap[] getPicArray() {
		return picArray;
	}
	public void setPicArray(Bitmap[] picArray) {
		this.picArray = picArray;
	}
	public Drawable getArcher() {
		return archer;
	}
	public void setArcher(Drawable archer) {
		this.archer = archer;
	}
	public Display getDisplay() {
		return display;
	}
	public void setDisplay(Display display) {
		this.display = display;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		if(x>0&&x<screenWidth-60){		
			this.x = x;
		}
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public  String getTag() {
		return TAG;
	}
	public int getImageCount() {
		return imageCount;
	}
	public Paint getTextPaint() {
		return textPaint;
	}
	public void setTextPaint(Paint textPaint) {
		this.textPaint = textPaint;
	}
	public Paint getRectPaint() {
		return rectPaint;
	}
	public void setRectPaint(Paint rectPaint) {
		this.rectPaint = rectPaint;
	}
	public int getGameScore() {
		return gameScore;
	}
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}	
	public BalloonDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(BalloonDataSource dataSource) {
		this.dataSource = dataSource;
	}

	//The Constructor initializes all the images
	//it starts the Thread
	//it initializes the height and width of the screen
	public MainGamePanel(Context context) {
		super(context);
		//setting the context in a Class variable
		this.context = context;

		//Loading the Sound files
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				// TODO Auto-generated method stub
				soundLoaded = true;
			}
		});
		soundId = soundPool.load(context, R.raw.water_drip, 1);

		//Log.d(TAG, "Game Panel constructor called");
		asset = context.getAssets();

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		for (int i = 0; i < picArray.length; i++) {
			picArray[i] = BitmapFactory.decodeResource(getResources(), R.drawable.balloon);
		}
		display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
		screenWidth = display.getWidth();
		screenHeight=display.getHeight();
		float aspectRatio=(float)screenWidth/screenHeight;
		Log.d(TAG,"aspect ratio : "+aspectRatio);
		Bullet.setRadius((int)(aspectRatio*10));
		archer = context.getResources().getDrawable(R.drawable.tank);
		grassTile = context.getResources().getDrawable(R.drawable.grass);
		blackTile = context.getResources().getDrawable(R.drawable.black_tile);
		pebble = context.getResources().getDrawable(R.drawable.pebble);
		arrowImage = BitmapFactory.decodeResource(getResources(),R.drawable.bullet);
		x = screenWidth/2 - archer.getIntrinsicWidth()/2;

		droid = new Balloon(picArray,screenHeight,screenWidth);
		arrow = new Arrow(arrowImage,BitmapFactory.decodeResource(getResources(), R.drawable.archer));

		thread = new MainThread(getHolder(),this);

		baseLayout = new RelativeLayout(context);
		baseLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		text = new TextView(context);
		timeElapsedView = new TextView(context);
		timeElapsedText = new TextView(context);
		scoreTextView = new TextView(context);
		scoreView = new TextView(context);
		
		baseLayout.addView(text);
		baseLayout.addView(timeElapsedView);
		baseLayout.addView(timeElapsedText);
		baseLayout.addView(scoreTextView);
		baseLayout.addView(scoreView);

		//sending the current thread instance to the droid class
		BalloonActivity.setGameThread(thread);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while(retry){
			try{
				thread.join();
				retry = false;
			}
			catch(InterruptedException exception){

			}
		}
	}

	/**
	 * This method is invoked automatically when a motion event (tap/release/drag etc) is generated
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// when touched Action_down is generated
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			//releasing the arrow on touch if the touch is inside the tank
			arrow.handleActionDown((int)event.getX(), (int)event.getY(),(int)x,screenHeight);

		}

		//for drag ACTION_MOVE is generated
		if (event.getAction() == MotionEvent.ACTION_MOVE) { 

		} 
		// when touch is released Action_UP is generated
		if (event.getAction() == MotionEvent.ACTION_UP) {  

		}  
		return true;
	}

	/**
	 * this method is invoked form Main thread. It's responsible for ---
	 * 		*detecting collision of balloons with side walls and toggling the direction on collision
	 * 		*detecting bullet hitting with any balloon
	 * 		*updating score by 10 on every such hit
	 * 		* calling update() for balloons position update
	 * 		* calling update() for bullets position update 
	 * 
	 */
	public void update(){

		for (int i = picArray.length-1; i>=0; i--) {

			// check collision of ith balloon with right wall if heading right 
			if (droid.getSpeed().getxDirecArr()[i] == Speed.DIRECTION_RIGHT  && droid.getImagePos()[i][0] + droid.getPicArray()[i].getWidth() / 2 >= getWidth()) {
				droid.getSpeed().toggleXDirection(i);
			}  

			// check collision of ith balloon with left wall if heading left  
			if (droid.getSpeed().getxDirecArr()[i] == Speed.DIRECTION_LEFT  && droid.getImagePos()[i][0] - droid.getPicArray()[i].getWidth() / 2 <= 0) {
				droid.getSpeed().toggleXDirection(i);  
			}  



			int j=i;
			//getting co-ordinates of top-left and bottom right corners of ith/jth balloon
			int balloonXbottom = droid.getImagePos()[j][0]+droid.getPicArray()[0].getWidth()/2;
			int balloonYbottom = droid.getImagePos()[j][1] + droid.getPicArray()[0].getHeight()/2;
			int balloonXtop = droid.getImagePos()[j][0]-droid.getPicArray()[0].getWidth()/2;
			int balloonYtop = droid.getImagePos()[j][1]- droid.getPicArray()[0].getHeight()/2;

			/*This loop checks for collision of ith/jth balloon with every bullet released  */
			for(int k=0;k<arrow.getBulletList().size();k++){


				Bullet bullet=arrow.getBulletList().get(k);
				//getting co-ordinates for 4 Pts(TOP,RIGHT,BOTOM,LEFT) on circumference of kth bullet in the list
				int bulletCorners[][]=bullet.getBulletCorners();

				/**
				 * Following if-else blks detect if any bullet has hit i/j th balloon. If found so then
				 * updates the score by 10 and sets the flag true for the affected(i/j th) balloon  
				 */

				//Checks if kth bullet's TOP hits/within ith/jth balloon
				if((bulletCorners[TOP][ORDINATE]<=balloonYbottom && bulletCorners[TOP][ORDINATE]>=balloonYtop) && (bulletCorners[TOP][ABSCISSA]<=balloonXbottom && bulletCorners[TOP][ABSCISSA]>=balloonXtop)&&(droid.getBalloonHitIndices()[j] != true)){						
					droid.getBalloonHitIndices()[j] = true;

					//Playing Sound on hitting a Balloon
					AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
					float volume = actualVolume / maxVolume;
					// Is the sound loaded already?
					if (soundLoaded) {
						soundPool.play(soundId, volume, volume, 1, 0, 1f);
					}

					gameScore+=10;						
				}
				//Checks if kth bullet's LEFT hits/within ith/jth balloon
				else if((bulletCorners[LEFT][ABSCISSA]>=balloonXtop && bulletCorners[LEFT][ABSCISSA]<=balloonXbottom) &&(bulletCorners[LEFT][ORDINATE]<=balloonYbottom && bulletCorners[LEFT][ORDINATE]>=balloonYtop)&&(droid.getBalloonHitIndices()[j] != true)){
					droid.getBalloonHitIndices()[j] = true;

					//Playing Sound on hitting a Balloon
					AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
					float volume = actualVolume / maxVolume;
					// Is the sound loaded already?
					if (soundLoaded) {
						soundPool.play(soundId, volume, volume, 1, 0, 1f);
					}

					gameScore+=10;						
				}
				//Checks if kth bullet's RIGHT hits/within ith/jth balloon
				else if((bulletCorners[RIGHT][ABSCISSA]>=balloonXtop && bulletCorners[RIGHT][ABSCISSA]<=balloonXbottom) && (bulletCorners[RIGHT][ORDINATE]<=balloonYbottom && bulletCorners[RIGHT][ORDINATE]>=balloonYtop)&&(droid.getBalloonHitIndices()[j] != true)){
					droid.getBalloonHitIndices()[j] = true;

					//Playing Sound on hitting a Balloon
					AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
					float volume = actualVolume / maxVolume;
					// Is the sound loaded already?
					if (soundLoaded) {
						soundPool.play(soundId, volume, volume, 1, 0, 1f);
					}

					gameScore+=10;					
				}
				//Checks if kth bullet's BOTTOM hits/within ith/jth balloon
				else if((bulletCorners[BOTTOM][ORDINATE]<=balloonYbottom && bulletCorners[BOTTOM][ORDINATE]>=balloonYtop)&&(bulletCorners[BOTTOM][ABSCISSA]<=balloonXbottom && bulletCorners[BOTTOM][ABSCISSA]>=balloonXtop)&&(droid.getBalloonHitIndices()[j] != true)){
					droid.getBalloonHitIndices()[j] = true;

					//Playing Sound on hitting a Balloon
					AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
					float volume = actualVolume / maxVolume;
					// Is the sound loaded already?
					if (soundLoaded) {
						soundPool.play(soundId, volume, volume, 1, 0, 1f);
					}

					gameScore+=10;
				}
				else{
					//Do nothing
				}

			}
			// Update the lone droid  
			try {
				droid.update();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arrow.update();
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {

		grassTile.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight()-30);
		grassTile.draw(canvas);

		pebble.setBounds(0, getMeasuredHeight()-30, getMeasuredWidth(), getMeasuredHeight());
		pebble.draw(canvas);

		rectPaint.setColor(Color.BLACK);
		canvas.drawRect(0f, 0f, screenWidth, (float)(0.1*screenHeight), rectPaint);
		
		int rectangleHeight = (int)(0.1*screenHeight);

		//Typeface typeface = Typeface.createFromAsset(asset,"fonts/HandmadeTypewriter.ttf");
		Typeface typeface = Typeface.SANS_SERIF;
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTypeface(typeface);
		textPaint.setTextSize(15);
		textPaint.setColor(Color.RED);
		//canvas.drawText("Time Left :", 10, 10, textPaint);
		//canvas.drawText("Score : "+gameScore, (int)(5+0.7*(screenWidth-10)), 10, textPaint);

		timeElapsedText.layout((int)(0.1*screenWidth), (int)(0.1*rectangleHeight), (int)(0.4*screenWidth), (int)(0.9*rectangleHeight));
		timeElapsedText.setBackgroundColor(Color.BLACK);
		timeElapsedText.setTextColor(Color.RED);
		timeElapsedText.setGravity(Gravity.CENTER);
		timeElapsedText.setText("Time Left : ");
		
		timeElapsedView.layout( (int)(0.4*screenWidth), (int)(0.1*rectangleHeight), (int)(0.5*screenWidth), (int)(0.9*rectangleHeight));
		timeElapsedView.setBackgroundColor(Color.BLACK);
		timeElapsedView.setTextColor(Color.RED);
		timeElapsedView.setGravity(Gravity.CENTER);
		
		scoreTextView.layout((int)(0.6*screenWidth), (int)(0.1*rectangleHeight), (int)(0.75*screenWidth), (int)(0.9*rectangleHeight));
		scoreTextView.setBackgroundColor(Color.BLACK);
		scoreTextView.setTextColor(Color.RED);
		scoreTextView.setGravity(Gravity.CENTER);
		scoreTextView.setText("Score : ");
		
		scoreView.layout((int)(0.75*screenWidth), (int)(0.1*rectangleHeight), (int)(0.9*screenWidth), (int)(0.9*rectangleHeight));
		scoreView.setBackgroundColor(Color.BLACK);
		scoreView.setTextColor(Color.RED);
		scoreView.setGravity(Gravity.CENTER);		
		
		//System.out.println("Base Layout drawn");
		baseLayout.draw(canvas);


		droid.draw(canvas);
		arrow.draw(canvas);

		archer.setBounds((int)x, getMeasuredHeight()-50,(int)x+40,getMeasuredHeight()-20);
		archer.draw(canvas);
		canvas.save();
	}

	/**
	 * Checks if it's new top 5  highscore and then saves it 
	 */
	public void checkAndSaveHighScore(){
		System.out.println("check method called, you better work");
		dataSource = new BalloonDataSource(context);
		dataSource.open();
		// finds if the current gameScore is a high score
		int isHighScore = dataSource.checkHighScore(Integer.toString(gameScore, 10));
		dataSource.close();
		
		if(isHighScore==1 || isHighScore==2){
			final int score = isHighScore;
			
			HighScoreDialog newDialog = new HighScoreDialog(context,dataSource,score,thread,gameScore);
			newDialog.show();			
		}
		else{
			thread.setRunning(false);
			((Activity)getContext()).finish();
		}
	}
	//Timer class
	public class CountdownTimer extends CountDownTimer
	{			
		public CountdownTimer(long startTime, long interval){

			super(startTime, interval);
		}


		@Override
		public void onFinish()
		{				
			timeElapsedView.setText("0");
			// custom dialog
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.gameover_screen);
			dialog.setTitle("			Game Over!");

			Button highScoreDialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			highScoreDialogButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();

					//checking if High Score is reached and then saves the new best score in database
					checkAndSaveHighScore();
					System.out.println("Exiting Thread");
					/*thread.setRunning(false);
					((Activity)getContext()).finish();*/
				}
			});
			dialog.show();
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			//System.out.println("on Tick calles");
			timeElapsedView.setText(String.valueOf(millisUntilFinished/1000));
			scoreView.setText(String.valueOf(gameScore));
		}
	}
}
