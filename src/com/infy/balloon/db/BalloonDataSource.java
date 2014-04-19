package com.infy.balloon.db;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BalloonDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private int positionCount;
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	public MySQLiteHelper getDbHelper() {
		return dbHelper;
	}
	public void setDbHelper(MySQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	public int getPositionCount() {
		return positionCount;
	}
	public void setPositionCount(int positionCount) {
		this.positionCount = positionCount;
	}
	public BalloonDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public void insert(String name,String score){
		Integer rowId = 0;
		Integer newRow;
		int i;
		int position=0;
		ContentValues contentValues = new ContentValues();
		contentValues.put(MySQLiteHelper.COLUMN1_NAME,name);
		contentValues.put(MySQLiteHelper.COLUMN2_NAME, score);
		contentValues.put(MySQLiteHelper.COLUMN3_NAME,new Date().toString());
		Cursor cursor = queryAll();
		cursor.moveToLast();
		
		
			System.out.println("Heeeeeeeeeeeeeeeeeeee");
			//Check for empty cursor - Normal insert
			if(cursor.getCount()==0){
				

				database.insert(MySQLiteHelper.TABLE_NAME, null, contentValues);			
			}
			//Cursor is not empty - Update
			else{
				for(i=0; i<cursor.getCount();i++){
					if((Integer.parseInt(cursor.getString(2))<Integer.parseInt(score))){

						System.out.println(cursor.getString(2)+ "is less than "+ score);
						System.out.println("rowId "+rowId);
						rowId= cursor.getInt(0);
						position=cursor.getPosition();
						System.out.println("pos "+position);
						cursor.moveToPrevious();
						continue;
					}
					else{
						break;
					}			
				}
				newRow = rowId.intValue();
				if(i==0){
					database.insert(MySQLiteHelper.TABLE_NAME, null, contentValues);
				}
				Cursor cursorToBeReplaced= database.query(MySQLiteHelper.TABLE_NAME, new String[]{MySQLiteHelper.COLUMN1_NAME,MySQLiteHelper.COLUMN2_NAME,MySQLiteHelper.COLUMN3_NAME}, MySQLiteHelper.COLUMN_ID+">=?", new String[]{rowId.toString()}, null, null, null);
				cursorToBeReplaced.moveToFirst();

				

				database.update(MySQLiteHelper.TABLE_NAME, contentValues, MySQLiteHelper.COLUMN_ID+"=?", new String[]{(newRow).toString()});


				database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID+">?", new String[]{rowId.toString()});
				ContentValues updateValues = new ContentValues();
				/*ContentValues contentValues = new ContentValues();
		contentValues.put(MySQLiteHelper.COLUMN1_NAME,name);
		contentValues.put(MySQLiteHelper.COLUMN2_NAME, score);
		contentValues.put(MySQLiteHelper.COLUMN3_NAME,new Date().toString());

		database.update(MySQLiteHelper.TABLE_NAME, contentValues, MySQLiteHelper.COLUMN_ID+"=?", new String[]{(rowId).toString()});*/



				for(i=0;i<cursorToBeReplaced.getCount();i++){
					System.out.println("cursor to be replaced "+cursorToBeReplaced.getString(0));
					updateValues.put(MySQLiteHelper.COLUMN1_NAME, cursorToBeReplaced.getString(0));
					updateValues.put(MySQLiteHelper.COLUMN2_NAME, cursorToBeReplaced.getString(1));
					updateValues.put(MySQLiteHelper.COLUMN3_NAME, cursorToBeReplaced.getString(2));

					//Update database
					//database.update(MySQLiteHelper.TABLE_NAME, updateValues, MySQLiteHelper.COLUMN_ID+"=?", new String[]{rowId.toString()});
					database.insert(MySQLiteHelper.TABLE_NAME, null, updateValues);

					cursorToBeReplaced.moveToNext();

				}
			}
		
	}

	public void deleteRows(){
		database.delete(MySQLiteHelper.TABLE_NAME, null, null);
	}
	
	public int checkHighScore(String score){
		Cursor cursor = queryAll();
		
		if(cursor.getCount()<5){
			System.out.println("whats my count ??"+cursor.getCount());
			return 1;
		}
		
		cursor.moveToLast();
		if(Integer.parseInt(score) > Integer.parseInt(cursor.getString(2))){
			return 2;
		}
		else{
			return 3;
		}
		
	}
	
	public void updateHighScore(String name,String score){
		Integer rowId = 0;
		Cursor cursor = queryAll();
		cursor.moveToFirst();
		do{
			if(Integer.parseInt(score) >= Integer.parseInt(cursor.getString(2))){
				positionCount = cursor.getPosition();
				rowId = cursor.getInt(0);
				break;
			}
		}
		while(cursor.moveToNext());
		
		ContentValues updateValues = new ContentValues();
		updateValues.put(MySQLiteHelper.COLUMN1_NAME, name);
		updateValues.put(MySQLiteHelper.COLUMN2_NAME, score);
		updateValues.put(MySQLiteHelper.COLUMN3_NAME,new Date().toString());
			
		for(int i=positionCount;i<cursor.getCount();i++){
			
			//getting row data at indexNo = positionCount from database which I need to replace
			Cursor cursorToBeReplaced= database.query(MySQLiteHelper.TABLE_NAME, new String[]{MySQLiteHelper.COLUMN1_NAME,MySQLiteHelper.COLUMN2_NAME,MySQLiteHelper.COLUMN3_NAME}, MySQLiteHelper.COLUMN_ID+"=?", new String[]{rowId.toString()}, null, null, null);
			cursorToBeReplaced.moveToFirst();
			
			//Update database
			database.update(MySQLiteHelper.TABLE_NAME, updateValues, MySQLiteHelper.COLUMN_ID+"=?", new String[]{rowId.toString()});
			
			updateValues.put(MySQLiteHelper.COLUMN1_NAME, cursorToBeReplaced.getString(0));
			updateValues.put(MySQLiteHelper.COLUMN2_NAME, cursorToBeReplaced.getString(1));
			updateValues.put(MySQLiteHelper.COLUMN3_NAME, cursorToBeReplaced.getString(2));
			
			rowId++;
		}
	}

	/*public int deleteAll(){
		return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
	}*/

	public Cursor queryAll(){
		//Balloon_HighScore score = new Balloon_HighScore();
		String[] columns = new String[]{"_id", MySQLiteHelper.COLUMN1_NAME,MySQLiteHelper.COLUMN2_NAME,MySQLiteHelper.COLUMN3_NAME};
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns,null, null, null, null, null);
		return cursor;
	}

}
