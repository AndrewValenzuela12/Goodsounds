package com.example.andrewvalenzuela.goodsounds;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by AndrewValenzuela on 4/28/18.
 */

public class AlbumAdapter2 extends BaseAdapter {
    // adapter takes the app itself and a list of data to display
    private Context myContext;
    private ArrayList<Album> myAlbumList;
    private LayoutInflater myInflater;
    private DatabaseHelper myDb;
    //constructor
    public AlbumAdapter2(Context myContext, ArrayList<Album> myAlbumList) {
        //initialize instance variable
        this.myContext = myContext;
        this.myAlbumList = myAlbumList;
        this.myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myDb = new DatabaseHelper(myContext);
    }

    //methods
    //a list of methods we need to override
    //gives ypu the number of Albums in the data source

    @Override
    public int getCount() {
        return myAlbumList.size();
    }

    // returns the item at specific positin in the data source

    @Override
    public Object getItem(int position) {
        return myAlbumList.get(position);
    }

    // returns the row id as associated with the specific position in the list
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AlbumAdapter2.ViewHolder holder;
        // check if the view already exists
        //if yes, you dont need to inflate and findViewbyID again
        if (convertView == null) {
            //inflate
            convertView = myInflater.inflate(R.layout.mylist, parent, false);
            //add the views to the holder
            holder = new AlbumAdapter2.ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.album_list_title);
            holder.thumbnailImageView = convertView.findViewById(R.id.albumInList);
            holder.artistTextView = convertView.findViewById(R.id.artist_list_title);
            holder.ratedRatingBar = convertView.findViewById(R.id.listratingBar);
            holder.shareButton = convertView.findViewById(R.id.shareButton);
            holder.deleteButton = convertView.findViewById(R.id.deleteButton);
            //add the holder to the view
            convertView.setTag(holder);
        } else {
            //get the view holder from convertView
            holder = (AlbumAdapter2.ViewHolder)convertView.getTag();
        }
        //get relevant subview of the row view
        TextView titleTextView = holder.titleTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TextView artistTextView = holder.artistTextView;
        RatingBar ratedRatingBar = holder.ratedRatingBar;
        Button shareButton = holder.shareButton;
        Button deleteButton = holder.deleteButton;
        //get corresponding recipe for each row
        final Album album = (Album)getItem(position);

        //update the row view's textviews ad imageView to display the information
        titleTextView.setText(album.title);
        artistTextView.setText(album.artist);
        ratedRatingBar.setRating(album.rating);

        //use Picasso library to load image from the image url;
        //if statement look at the load method
        if (album.imageUrl.isEmpty()) {
            thumbnailImageView.setImageResource(R.drawable.ic_launcher_background);
        } else {
            Picasso.with(myContext).load(album.imageUrl).into(thumbnailImageView);
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(album.title, album.artist, album.comment, album.rating);
            }
        });

        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                int positionToRemove = (int)v.getTag(); //get the position of the view to delete stored in the tag
                removeItem(positionToRemove);
                myDb.deleteData(album.id);
                notifyDataSetChanged(); //remove the item
            }
        });

        return convertView;
    }

    public void removeItem(int position){
        myAlbumList.remove(position);
        notifyDataSetChanged(); //refresh your listview based on new data

    }

    // viewholder
    // is used to customize what you want to put into the view
    // it depends on the layout design of your row
    // this will be a private static class you have to define
    private static class ViewHolder {
        public TextView titleTextView;
        public ImageView thumbnailImageView;
        public TextView artistTextView;
        public RatingBar ratedRatingBar;
        public Button shareButton;
        public Button deleteButton;
    }


    //intent is used to pass information between activities
    // intent -> package
    // sender, receiver
    private void launchActivity(String albumTitle, String artist, String comments, Integer rating) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String ratingText = rating + " stars.";
        String text = "I rated the album " + albumTitle + " by " + artist + " " + ratingText + " This is what I thought about it: " + comments;
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        myContext.startActivity(shareIntent);
    }


}
