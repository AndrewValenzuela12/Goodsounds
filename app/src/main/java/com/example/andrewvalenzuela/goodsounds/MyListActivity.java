package com.example.andrewvalenzuela.goodsounds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        ArrayList<Album> myAlbums = new ArrayList<>();
        // need to pull data from database and add Album objects into arraylist



        AlbumAdapter2 adapter = new AlbumAdapter2(this, myAlbums);
        mFinalListView.setAdapter(adapter);
    }



    public void onClickBackHome(View view){this.finish();}
}