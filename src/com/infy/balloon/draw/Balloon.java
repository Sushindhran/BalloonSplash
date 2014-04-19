package com.infy.balloon.draw;

import java.util.Random;

import com.infy.balloon.game.MainThread;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Balloon {
	private static final String TAG = Balloon.class.getSimpleName();
	private final int imageCount = 6;
	private Bitmap bitmap;
	private boolean touched;
	private int screenHeight;
	private Speed speed = new Speed();
	private Bitmap[] picArray = new Bitmap[imageCount];
	private int[][] imagePos = new int[imageCount][2]; 
	private boolean[] balloonHitIndices = new boolean[imageCount];
	private boolean breakFlag;
	private int screenWidth;
	//private MainThread thread;
	
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
	public static String getTag() {
		return TAG;
	}
	public boolean[] getBalloonHitIndices() {
		return balloonHitIndices;
	}
	public void setBalloonHitIndices(boolean[] balloonHitIndices) {
		this.balloonHitIndices = balloonHitIndices;
	}
	public int[][] getImagePos() {
		return imagePos;
	}
	public void setImagePos(int[][] imagePos) {
		this.imagePos = imagePos;
	}
	public int getImageCount() {
		return imageCount;
	}
	public Bitmap[] getPicArray() {
		return picArray;
	}
	public void setPicArray(Bitmap[] picArray) {
		this.picArray = picArray;
	}
	public Speed getSpeed() {
		return speed;
	}
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	public boolean isBreakFlag() {
		return breakFlag;
	}
	public void setBreakFlag(boolean breakFlag) {
		this.breakFlag = breakFlag;
	}
	
	public Balloon(Bitmap picArray[],int screenHeight,int screenWidth) {
		this.screenHeight = screenHeight;
		this.picArray = picArray;
		this.screenWidth=screenWidth;
		generateBallonsPos();
		
	}


	/**
	 * This method sets coordinate positions for all the balloons. 
	 * It sets them at random position on X-axis and at equal distance in Y-axis
	 */
	public void generateBallonsPos(){
		Random randomGenerator = new Random();
		for (int i = 0; i < picArray.length; i++) {
			// setting random value as x-coordinate for each balloon
			// random values for x coordinate is genereted within screenWidth
			imagePos[i][0]=randomGenerator.nextInt(screenWidth);
			//y-coordinate for first balloon is set to 30
			if(i==0)
				imagePos[i][1]=(int)(0.1*screenHeight);
			else
			//y-coordinates for rest of the balloons are set so they are equi-distant and are within screen height	
			// 30 is the height of top bar for score & timer
				imagePos[i][1]=imagePos[i-1][1]+((screenHeight-(int)(0.1*screenHeight))/imageCount);
		}
	}
	
	/**
	 * This method updates the position of balloons and is responsible for ballons' movement in X-Axis.
	 * Also keeps track if all balloons are hit then refreshes the screen with balloons in new positions
	 * @throws InterruptedException 
	 */
	public void update() throws InterruptedException{
		int hitBalloonCount = 0;
		/*if(!touched){
			x += (speed.getXv() * speed.getxDirection());
			y += (speed.getYv() * speed.getyDirection());
		}*/
		
		for (int i = 0; i < picArray.length; i++) {
			/*if(imagePos[i][1] >= (0.80*screenHeight)){
				break;
			}*/
			imagePos[i][0] += speed.getxSpeedArr()[i] * speed.getxDirecArr()[i];
			imagePos[i][1] += speed.getySpeedArr()[i] * speed.getyDirecArr()[i];
		}
		
		/*balloonHitIndices[imageCount] is an array having boolean flags w.r.t each balloon. 
		 * balloonHitIndices[i]: true value indicates the ith balloon has been hit 
		 * 					   : false value represents the ith balloon has Not been hit
			
		This following loop finds out how many balloons have been hit*/
		
		for(int i=0;i<imageCount;i++){
			if(getBalloonHitIndices()[i] ==true){
				hitBalloonCount++;
			}
				
		}
		
	
	/* If checks if all the balloons have been hit*/
		if(hitBalloonCount==imageCount){
			hitBalloonCount=0; //resetting hitBalloonCount
			
			//Loop sets all the bullet hit indices to false when all the balloons are hit
			for (int j = 0; j < imageCount; j++) {
				//Log.d(TAG, "balloon flags set to false");
				getBalloonHitIndices()[j] = false;
			}
		
			MainThread.sleep(10);
			// invoke method to refresh screen
			generateBallonsPos();
		}
	}
	
	public void draw(Canvas canvas){
		//canvas.drawBitmap(bitmap, x- (bitmap.getWidth()/2),y-(bitmap.getHeight()/2), null);
		for (int i = 0; i < picArray.length; i++) {
			
			//Drawing Balloons only to fit up to 80% of the screen height
		/*	if(imagePos[i][1] >= (0.80*screenHeight)){
				breakFlag = true;
				break;
			}*/
			//skipping the drawing if ballloon has been hit by a bullet
			if(balloonHitIndices[i]==true){
				continue;
			}
			bitmap=picArray[i];
			//canvas.drawBitmap(bitmap, imagePos[i][0] - (bitmap.getWidth()/2),imagePos[i][1] -(bitmap.getHeight()/2), null);
			canvas.drawBitmap(bitmap, imagePos[i][0],imagePos[i][1], null);
		}
	}
}
