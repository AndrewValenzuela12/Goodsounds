package com.example.andrewvalenzuela.goodsounds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by ellenshin on 4/26/18.
 */

public class AlbumDetailActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private Context myContext;
    private TextView myTitleTextView;
    private TextView myArtistTextView;
    private Button myAddButton;
    private ImageView myImageView;
    private RatingBar myRatingBar;
    private EditText myCommentBox;
    public String album_title;
    public String album_artist;
    public String album_url;
    public int album_rating;
    public String album_comment;
    public String btn_name;
    public String album_id;
    public int album_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);
        myDb = new DatabaseHelper(this);

        myContext = this;
        myTitleTextView = findViewById(R.id.selected_AlbumTitle);
        myArtistTextView = findViewById(R.id.selected_ArtistName);
        myAddButton = findViewById(R.id.addtoList);
        myImageView = findViewById(R.id.selected_imageView);
        myRatingBar = findViewById(R.id.ratingBar);
        myCommentBox = findViewById(R.id.commentbox);

        // change welcomeText to display: username + welcome back!
        album_title = this.getIntent().getExtras().getString("album_title");
        album_artist = this.getIntent().getExtras().getString("album_artist");
        album_url = this.getIntent().getExtras().getString("album_url");
        album_rating = this.getIntent().getExtras().getInt("album_rating");
        album_comment = this.getIntent().getExtras().getString("album_comment");
        btn_name = this.getIntent().getExtras().getString("btn_name");
        album_id = this.getIntent().getExtras().getString("album_id");
        album_position = this.getIntent().getExtras().getInt("album_position");

        myAddButton.setText(btn_name);

        if (album_url.isEmpty()) {
            myImageView.setImageResource(R.drawable.ic_launcher_background);
        } else {
            Picasso.with(myContext).load(album_url).into(myImageView);
        }

        myTitleTextView.setText(album_title);
        myArtistTextView.setText(album_artist);
        myRatingBar.setRating(album_rating);
        myCommentBox.setText(album_comment);


    }

    public void onClickBack(View view) {
        this.finish();
    }

    public void onClickAdd(View view) {
        if (btn_name.equals("ADD")) {
            boolean isInserted = myDb.insertData(album_title, album_artist, album_url, Math.round(myRatingBar.getRating()), myCommentBox.getText().toString());
            if (isInserted == true){
                // I inserted correctly
                Toast.makeText(AlbumDetailActivity.this, "New album added!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(AlbumDetailActivity.this, "There was a problem :(", Toast.LENGTH_LONG).show();
            }
        } else {
            boolean isUpdated = myDb.updateData(album_id, album_title, album_artist, album_url, Math.round(myRatingBar.getRating()), myCommentBox.getText().toString());
            if (isUpdated == true){
                // I inserted correctly
                Toast.makeText(AlbumDetailActivity.this, "Updated!", Toast.LENGTH_LONG).show();
                Intent updateIntent = new Intent();
                updateIntent.putExtra("position", album_position);
                updateIntent.putExtra("new_rating", Math.round(myRatingBar.getRating()));
                updateIntent.putExtra("new_comment", myCommentBox.getText().toString());
                setResult(RESULT_OK, updateIntent);
                finish();
            }
            else{
                Toast.makeText(AlbumDetailActivity.this, "There was a problem :(", Toast.LENGTH_LONG).show();
            }
        }
        this.finish();
    }

}
