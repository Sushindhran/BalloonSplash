package com.infy.balloon.draw;

public class Speed {
	public static final int DIRECTION_RIGHT = 1;  
	public static final int DIRECTION_LEFT  = -1;  
	public static final int DIRECTION_UP    = -1;  
	public static final int DIRECTION_DOWN  = 1;  
	private int xDirection = DIRECTION_RIGHT;  
	private int yDirection = DIRECTION_DOWN;
	private float arrowSpeedX;   // velocity value on the X axis  
	private float arrowSpeedY;   // velocity value on the Y axis  	
	private int arrowXDirection;
	private int arrowYDirection;	
	private final int imageCount = 6;	
	private float[] xSpeedArr = new float[imageCount];	
	private float[] ySpeedArr = new float[imageCount];	
	private int[] xDirecArr = new int[imageCount];	
	private int[] yDirecArr = new int[imageCount];
	
	public float[] getySpeedArr() {
		return ySpeedArr;
	}
	public void setySpeedArr(float[] ySpeedArr) {
		this.ySpeedArr = ySpeedArr;
	}
	public int[] getxDirecArr() {
		return xDirecArr;
	}
	public void setxDirecArr(int[] xDirecArr) {
		this.xDirecArr = xDirecArr;
	}
	public int[] getyDirecArr() {
		return yDirecArr;
	}
	public void setyDirecArr(int[] yDirecArr) {
		this.yDirecArr = yDirecArr;
	}
	public int getImageCount() {
		return imageCount;
	}	
	public int getArrowXDirection() {
		return arrowXDirection;
	}
	public void setArrowXDirection(int arrowXDirection) {
		this.arrowXDirection = arrowXDirection;
	}
	public int getArrowYDirection() {
		return arrowYDirection;
	}
	public void setArrowYDirection(int arrowYDirection) {
		this.arrowYDirection = arrowYDirection;
	}
	public float[] getxSpeedArr() {
		return xSpeedArr;
	}
	public void setxSpeedArr(float[] xSpeedArr) {
		this.xSpeedArr = xSpeedArr;
	}
	public static int getDirectionRight() {
		return DIRECTION_RIGHT;
	}
	public static int getDirectionLeft() {
		return DIRECTION_LEFT;
	}
	public static int getDirectionUp() {
		return DIRECTION_UP;
	}
	public static int getDirectionDown() {
		return DIRECTION_DOWN;
	}
	public int getxDirection() {  
		return xDirection;  
	}  
	public float getArrowSpeedX() {
		return arrowSpeedX;
	}
	public void setArrowSpeedX(float arrowSpeedX) {
		this.arrowSpeedX = arrowSpeedX;
	}
	public float getArrowSpeedY() {
		return arrowSpeedY;
	}
	public void setArrowSpeedY(float arrowSpeedY) {
		this.arrowSpeedY = arrowSpeedY;
	}
	public void setxDirection(int xDirection) {  
		this.xDirection = xDirection;  
	}  
	public int getyDirection() {  
		return yDirection;  
	}
	public void setyDirection(int yDirection) {  
		this.yDirection = yDirection;  
	}

	public Speed() {  
		
		//initializing speed & direction for ballons movement
		for (int i = 0; i < imageCount; i++) {
			xSpeedArr[i] = 1;
			ySpeedArr[i] = 0;
			xDirecArr[i] = DIRECTION_RIGHT;
			yDirecArr[i] = DIRECTION_DOWN;
		}

	 
		//initializing speed & direction for arrow movement
		arrowSpeedX = 0;
		arrowSpeedY = 2;
		arrowYDirection = DIRECTION_UP;

	}  
	  

	// changes the direction on the X axis  
	public void toggleXDirection(int imageIndex) {  
		xDirecArr[imageIndex] = xDirecArr[imageIndex]* -1;
	}  


	// changes the direction on the Y axis  

	public void toggleYDirection(int imageIndex) {  
		yDirecArr[imageIndex] = yDirecArr[imageIndex]* -1;
	}  
	/*// changes the direction on the X axis  

	public void toggleXDirection() {  

		xDirection = xDirection * -1;

	}  


	// changes the direction on the Y axis  

	public void toggleYDirection() {  

		yDirection = yDirection * -1;  

	}  
*/
}
