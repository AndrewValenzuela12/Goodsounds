package com.example.andrewvalenzuela.goodsounds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ellenshin on 4/28/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Album.db";
    // name the table
    public static final String TABLE_NAME = "album_table";
    // create the column names
    public static final String COL_0 = "ID";
    public static final String COL_1 = "TITLE";
    public static final String COL_2 = "ARTIST";
    public static final String COL_3 = "URL";
    public static final String COL_4 = "RATING";
    public static final String COL_5 = "COMMENT";

    // I want to save this table into a file named student.db
    // within the file, you have the table, within the table, you have the columns

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        // calling the constructor from the parent class
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating the table with the columns and their data type
        // SQL statement
        sqLiteDatabase.execSQL("create table " + TABLE_NAME
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, ARTIST TEXT," +
                " URL TEXT,RATING INTEGER, COMMENT INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // i -> old version, i1 -> new version
        // if the table exists, you delete the table and then create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // insert row
    // write a method that returns true if the data has been successfully inserted into the table
    public boolean insertData(String title, String artist, String url, int rating, String comment){

        // get the database you want to insert into
        SQLiteDatabase db = this.getWritableDatabase();

        // get the content values
        ContentValues cv = new ContentValues();
        // put the value into each column
        cv.put(COL_1, title);
        cv.put(COL_2, artist);
        cv.put(COL_3, url);
        cv.put(COL_4, rating);
        cv.put(COL_5, comment);

        // call insert method
        long result = db.insert(TABLE_NAME, null, cv);
        // if it is correct, return true
        return (result != -1);

        // else false

    }

    // update row
    public boolean updateData(String id, String title, String artist, String url, int rating, String comment){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, title);
        cv.put(COL_2, artist);
        cv.put(COL_3, url);
        cv.put(COL_4, rating);
        cv.put(COL_5, comment);

        db.update(TABLE_NAME, cv, "ID = ?", new String[]{id});
        return true;
    }
    // show rows

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    // delete rows
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public ArrayList<Album> getAllObjects()
    {
        // Select All Query
        //String selectQuery = "SELECT * FROM SOME_TABLE";
        // Get the isntance of the database
        SQLiteDatabase db = this.getWritableDatabase();
        //get the cursor you're going to use
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        //this is optional - if you want to return one object
        //you don't need a list
        ArrayList<Album> objectList = new ArrayList<Album>();

        //you should always use the try catch statement incase
        //something goes wrong when trying to read the data
        try
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    //the .getString(int x) method of the cursor returns the column
                    //of the table your query returned
                    Album object = new Album(cursor.getString(1),
                            cursor.getString(3),cursor.getString(2));
                    Log.d("Album Rating", Integer.toString(Integer.parseInt(cursor.getString(4))));
                    object.rating = Integer.parseInt(cursor.getString(4));
                    object.comment = cursor.getString(5);
                    object.id = Integer.parseInt(cursor.getString(0));

                    // Adding contact to list
                    objectList.add(object);
                } while (cursor.moveToNext());
            }
        }
        catch (SQLiteException e)
        {
            Log.d("SQL Error", e.getMessage());
            return null;
        }
        finally
        {
            //release all your resources
            cursor.close();
            db.close();
        }
        return objectList;
    }

}

