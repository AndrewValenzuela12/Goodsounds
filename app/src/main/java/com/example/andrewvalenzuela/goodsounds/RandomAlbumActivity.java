package com.example.andrewvalenzuela.goodsounds;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ellenshin on 4/28/18.
 */

public class RandomAlbumActivity extends AppCompatActivity {

    private TextView myAlbumTitle;
    private TextView myAlbumArtist;
    private ImageView myAlbumImage;
    private TextView mySimilarArtists;
    private Context myContext;

    public String album_title;
    public String album_artist;
    public String album_url;
    public String album_similar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake);

        myAlbumTitle = findViewById(R.id.suggested_AlbumTitle);
        myAlbumArtist = findViewById(R.id.suggested_ArtistName);
        myAlbumImage = findViewById(R.id.shake_imageView);
        mySimilarArtists = findViewById(R.id.suggested_SimilarArtists);
        myContext = this;

        album_title = this.getIntent().getExtras().getString("album_title");
        album_artist = this.getIntent().getExtras().getString("album_artist");
        album_url = this.getIntent().getExtras().getString("album_url");
        album_similar = this.getIntent().getExtras().getString("similar");

        Picasso.with(myContext).load(album_url).into(myAlbumImage);

        if (album_url.isEmpty()) {
            myAlbumImage.setImageResource(R.drawable.ic_launcher_background);
        } else {
            Picasso.with(myContext).load(album_url).into(myAlbumImage);
        }

        myAlbumTitle.setText(album_title);
        myAlbumArtist.setText(album_artist);
        mySimilarArtists.setText(album_similar);
    }

    public void onClickExit(View view) {
        this.finish();
    }
}
