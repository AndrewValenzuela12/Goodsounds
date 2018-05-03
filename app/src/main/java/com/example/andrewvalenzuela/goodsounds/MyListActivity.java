package com.example.andrewvalenzuela.goodsounds;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndrewValenzuela on 4/28/18.
 */


public class MyListActivity extends AppCompatActivity {
    private ListView mFinalListView;
    private Album selectedAlbum;
    private Context mContext;
    DatabaseHelper myDb;
    public ArrayList<Album> myAlbums;
    private View selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_mylist);
        mFinalListView = findViewById(R.id.final_mylist_view);
        myDb = new DatabaseHelper(this);
        mContext = this;
        myAlbums = myDb.getAllObjects();
        // need to pull data from database and add Album objects into arraylist

//        Album dummy = new Album("hello", "https://lastfm-img2.akamaized.net/i/u/34s/eec47022dcb746e290fed001b30cceff.png", "lalala");
//        dummy.rating = 4;
//        dummy.comment = "this is my comment";
//
//        myAlbums.add(dummy);



        AlbumAdapter2 adapter = new AlbumAdapter2(this, myAlbums);
        mFinalListView.setAdapter(adapter);

        mFinalListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MyListActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                selectedAlbum = myAlbums.get(position);
                selectedItem = mFinalListView.getChildAt(position - mFinalListView.getFirstVisiblePosition());
                // create my intent package
                // add all the information needed for detail page
                // startActivity with that intent

                // explicit
                Intent detailIntent = new Intent(mContext, AlbumDetailActivity.class);
                // put title and and instruction URL
                detailIntent.putExtra("album_id", selectedAlbum.id);
                detailIntent.putExtra("album_title", selectedAlbum.title);
                detailIntent.putExtra("album_artist", selectedAlbum.artist);
                detailIntent.putExtra("album_url", selectedAlbum.imageUrl);
                detailIntent.putExtra("album_rating", selectedAlbum.rating);
                detailIntent.putExtra("album_comment", selectedAlbum.comment);
                detailIntent.putExtra("album_position", position);
                detailIntent.putExtra("btn_name", "UPDATE");

                startActivityForResult(detailIntent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);
                Album changedAlbum = myAlbums.get(position);
                changedAlbum.rating = data.getIntExtra("new_rating", 0);
                changedAlbum.comment = data.getStringExtra("new_comment");
                myAlbums.set(position, changedAlbum);
                myAlbums = myDb.getAllObjects();
                ((BaseAdapter) mFinalListView.getAdapter()).notifyDataSetChanged();
        }
    }

    public void onClickBackHome(View view){this.finish();}

}