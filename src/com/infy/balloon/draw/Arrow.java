package com.infy.balloon.draw;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Arrow {
	private static final String TAG = Arrow.class.getSimpleName();
	private Bitmap arrowImage;
	private Bitmap archerImage;
	private static boolean touched;
	private int arrowPosX;
	private int arrowPosY;
	List<Bullet> bulletList=new ArrayList<Bullet>();
	private Speed speed = new Speed();
	private static int bulletCount;

	public List<Bullet> getBulletList() {
		return bulletList;
	}
	public void setBulletList(List<Bullet> bulletList) {
		this.bulletList = bulletList;
	}
	public static int getBulletCount() {
		return bulletCount;
	}
	public static void setBulletCount(int bulletCount) {
		Arrow.bulletCount = bulletCount;
	}
	public Bitmap getArcherImage() {
		return archerImage;
	}
	public void setArcherImage(Bitmap archerImage) {
		this.archerImage = archerImage;
	}
	public static String getTag() {
		return TAG;
	}
	public Speed getSpeed() {
		return speed;
	}
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}
	public int getArrowPosX() {
		return arrowPosX;
	}
	public void setArrowPosX(int arrowPosX) {
		this.arrowPosX = arrowPosX;
	}
	public int getArrowPosY() {
		return arrowPosY;
	}
	public void setArrowPosY(int arrowPosY) {
		this.arrowPosY = arrowPosY;
	}
	public static boolean isTouched() {
		return touched;
	}
	public static void setTouched(boolean touched) {		
		Arrow.touched = touched;
	}
	public Bitmap getArrowImage() {
		return arrowImage;
	}
	public void setArrowImage(Bitmap arrowImage) {
		this.arrowImage = arrowImage;
	}

	//Constructor initializing the Arrow and the Archer Image
	public Arrow(Bitmap arrow,Bitmap archer) {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "Arrow constructor called");
		arrowImage = arrow;
		archerImage = archer;
	}

	//This method draws the Bullet on Screen
	public void draw(Canvas canvas){

		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		/*if(isTouched()){
			canvas.drawCircle(arrowPosX, arrowPosY, 4, paint);

		}*/
		for(int i=0;i<bulletCount;i++){
			canvas.drawCircle(bulletList.get(i).getBulletPosX(), bulletList.get(i).getBulletPosY(), Bullet.getRadius(), paint);
			//canvas.drawb
		}


	}

	//This method checks if the touch registered is within the archer image, if yes, 
	//it sets a boolean to true
	public void handleActionDown(int eventX,int eventY,int x,int height){



		if ( eventX >= x  && eventX <= (x+60) ) {  
			if ( eventY <= height  && eventY >= (height-60) ) {  

				// adding a new bullet in the list on every touch inside tank
				Bullet bullet = new Bullet();
				bullet.setBulletPosX(x+20);
				bullet.setBulletPosY(height-54);
				int cornerArray[][] = new int[4][2];
				cornerArray[0][0] = bullet.getBulletPosX();
				cornerArray[0][1] = bullet.getBulletPosY() - Bullet.getRadius();
				cornerArray[1][0] = bullet.getBulletPosX() + Bullet.getRadius();
				cornerArray[1][1] = bullet.getBulletPosY();
				cornerArray[2][0] = bullet.getBulletPosX();
				cornerArray[2][1] = bullet.getBulletPosY() + Bullet.getRadius();
				cornerArray[3][0] = bullet.getBulletPosX() - Bullet.getRadius();
				cornerArray[3][1] = bullet.getBulletPosY();
				bullet.setBulletCorners(cornerArray);
				//Log.d(TAG,"bullet has been released");
				bulletList.add(bulletCount,bullet);


				bulletCount++;
			} 
			else {  
				setTouched(false);  
			}  
		}
		else {  
			setTouched(false);  
		}  
	}

	//This method is responsible for updating the bullet values so that it 
	//moves upwards.
	public void update(){


		for(int i=0;i<bulletCount;i++){
			int bulletY = bulletList.get(i).getBulletPosY();
			if(bulletY>0){
				int bulletCorners[][] = new int[4][2];
				Bullet bullet = bulletList.get(i);
				int newBulletY=bulletY+(int)speed.getArrowSpeedY() * speed.getArrowYDirection();
				bullet.setBulletPosY(newBulletY);
				bulletCorners[0][0] = bullet.getBulletPosX();
				bulletCorners[0][1] = newBulletY - Bullet.getRadius();
				bulletCorners[1][0] = bullet.getBulletPosX() + Bullet.getRadius();
				bulletCorners[1][1] = newBulletY;
				bulletCorners[2][0] = bullet.getBulletPosX();
				bulletCorners[2][1] = newBulletY + Bullet.getRadius();
				bulletCorners[3][0] = bullet.getBulletPosX() - Bullet.getRadius();
				bulletCorners[3][1] = newBulletY;
				bulletList.get(i).setBulletCorners(bulletCorners);
			}
			else{
				bulletList.get(i).setReachTop(true);
			}
		}

		// this loop removes all those bullets from the bulletList which have reached TOP 
		for(int i=0;i<bulletList.size();i++){
			if(bulletList.get(i).isReachTop()==true){
				bulletList.remove(i);
				bulletCount--;
			}	
		}
	}
}
