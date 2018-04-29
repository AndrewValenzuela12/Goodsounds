package com.example.andrewvalenzuela.goodsounds;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndrewValenzuela on 4/28/18.
 */


public class MyListActivity extends AppCompatActivity {
    private ListView mFinalListView;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_mylist);
        mFinalListView = findViewById(R.id.final_mylist_view);
        myDb = new DatabaseHelper(this);

        ArrayList<Album> myAlbums = myDb.getAllObjects();
        // need to pull data from database and add Album objects into arraylist

        Album dummy = new Album("hello", "https://lastfm-img2.akamaized.net/i/u/34s/eec47022dcb746e290fed001b30cceff.png", "lalala");
        dummy.rating = 4;
        dummy.comment = "this is my comment";

        myAlbums.add(dummy);



        AlbumAdapter2 adapter = new AlbumAdapter2(this, myAlbums);
        mFinalListView.setAdapter(adapter);
    }


    public void onClickBackHome(View view){this.finish();}

}