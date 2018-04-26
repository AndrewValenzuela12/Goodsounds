package com.example.andrewvalenzuela.goodsounds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ellenshin on 4/26/18.
 */

public class AlbumDetailActivity extends AppCompatActivity {

    private Context myContext;
    private TextView myTitleTextView;
    private TextView myArtistTextView;
    private Button myAddButton;
    private ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        myContext = this;
        myTitleTextView = findViewById(R.id.selected_AlbumTitle);
        myArtistTextView = findViewById(R.id.selected_ArtistName);
        myAddButton = findViewById(R.id.addtoList);
        myImageView = findViewById(R.id.selected_imageView);

        myAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //extract data from stars and comment box and save them using local database
                //along with the album details
            }
        });

        // change welcomeText to display: username + welcome back!
        String album_title = this.getIntent().getExtras().getString("album_title");
        String album_artist = this.getIntent().getExtras().getString("album_artist");
        String album_url = this.getIntent().getExtras().getString("album_url");

        Picasso.with(myContext).load(album_url).into(myImageView);
        myTitleTextView.setText(album_title);
        myArtistTextView.setText(album_artist);

    }
}
