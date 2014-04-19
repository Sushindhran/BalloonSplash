package com.infy.balloon.draw;

public class Bullet {
	private int bulletPosX;
	private int bulletPosY;
	private static int radius = 0;
	private int bulletCorners[][]=new int[4][2];
	private boolean reachTop;
	
	public int getBulletPosX() {
		return bulletPosX;
	}
	public void setBulletPosX(int bulletPosX) {
		this.bulletPosX = bulletPosX;
	}
	public int getBulletPosY() {
		return bulletPosY;
	}
	public void setBulletPosY(int bulletPosY) {
		this.bulletPosY = bulletPosY;
	}
	public static int getRadius() {
		return radius;
	}
	public static void setRadius(int radius) {
		Bullet.radius = radius;
	}
	public int[][] getBulletCorners() {
		return bulletCorners;
	}
	public void setBulletCorners(int[][] bulletCorners) {
		this.bulletCorners = bulletCorners;
	}
	public boolean isReachTop() {
		return reachTop;
	}
	public void setReachTop(boolean reachTop) {
		this.reachTop = reachTop;
	}
}
