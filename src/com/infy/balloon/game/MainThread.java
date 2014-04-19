package com.infy.balloon.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


public class MainThread extends Thread{
	private static final String TAG = MainThread.class.getSimpleName();
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		// TODO Auto-generated constructor stub
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	@Override
	public void run(){
		Canvas canvas;
		long tickCount = 0L;
		Log.d(TAG, "Starting Game Loop!");
		while(running){
			tickCount++;
			//update game state
			//render it to screen
			canvas = null;  

			// try locking the canvas for exclusive pixel editing on the surface  

			try {  

			canvas = this.surfaceHolder.lockCanvas();  

			synchronized (surfaceHolder) {  

			// update game state 
			this.gamePanel.update();
			this.gamePanel.onDraw(canvas);  

			}  

			} finally {  

			// in case of an exception the surface is not left in  

			// an inconsistent state  

			 if (canvas != null) {  

			surfaceHolder.unlockCanvasAndPost(canvas);  

			}  
			}   // end finally  

			
		}
		Log.d(TAG, "Game loop executed : "+tickCount+" times");
	}
}
