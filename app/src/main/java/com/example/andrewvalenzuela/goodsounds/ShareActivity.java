package com.example.andrewvalenzuela.goodsounds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by AndrewValenzuela on 4/19/18.
 */

public class ShareActivity extends AppCompatActivity{
    Button ShareButton;
    TextView artistName;
    TextView albumName;
    RatingBar myRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylist);
        ShareButton = findViewById(R.id.shareButton);
        artistName = findViewById(R.id.artist_list_title);
        albumName = findViewById(R.id.album_list_title);
        myRatingBar = findViewById(R.id.ratingBar);
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String artistname = artistName.getText().toString();
                String albumname = albumName.getText().toString();
                int rating = myRatingBar.getNumStars();
                String ratingText = "Rating " + rating + " Stars";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, albumname);
                shareIntent.putExtra(Intent.EXTRA_TEXT, artistname);
                shareIntent.putExtra(Intent.EXTRA_TEXT, ratingText);
                startActivity(Intent.createChooser(shareIntent, "Share Using..."));

            }
        });

    }

}
