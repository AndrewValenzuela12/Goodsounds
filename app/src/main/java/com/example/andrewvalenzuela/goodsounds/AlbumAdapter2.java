package com.example.andrewvalenzuela.goodsounds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AndrewValenzuela on 4/28/18.
 */

public class AlbumAdapter2 extends BaseAdapter {
    // adapter takes the app itself and a list of data to display
    private Context myContext;
    private ArrayList<Album> myAlbumList;
    private LayoutInflater myInflater;

    //constructor
    public AlbumAdapter2(Context myContext, ArrayList<Album> myAlbumList) {
        //initialize instance variable
        this.myContext = myContext;
        this.myAlbumList = myAlbumList;
        this.myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        //get corresponding recipe for each row
        final Album album = (Album)getItem(position);

        //update the row view's textviews ad imageView to display the information
        titleTextView.setText(album.title);
        artistTextView.setText(album.artist);
        ratedRatingBar.setRating(album.rating);

        //use Picasso library to load image from the image url;
        //if statement look at the load method
        Picasso.with(myContext).load(album.imageUrl).into(thumbnailImageView);

        return convertView;
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
    }


    //intent is used to pass information between activities
    // intent -> package
    // sender, receiver

}
