package com.example.andrewvalenzuela.goodsounds;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ellenshin on 4/18/18.
 */

public class Album {
    // instance variables or fields
    public String title;
    public String imageUrl;
    public String artist;
    public int rating;
    public String comment;

    // constructor
    // default
    public Album(String title, String imageUrl, String artist) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.rating = 0;
        this.comment = "";
    }
    // method
    // static methods that read the json file in and load into Recipe

    // static method that loads our recipes.json using the helper method
    // this method will return an array list of recipes constructed from the JSON file
    public static ArrayList<Album> getAlbumList(JSONArray albumArray, Context context) {
        ArrayList<Album> albumList = new ArrayList<>();

        // try to read from JSON file
        // get information by using the tags
        // construct a Recipe Object for each recipe in JSON
        // add the object to arraylist
        // return arraylist
        try {
            JSONArray albums = albumArray;

            // for loop to go through each recipe in your recipes array
            for (int i = 0; i < albums.length(); i++) {
                Album album = new Album("","","");
                album.title = albums.getJSONObject(i).getString("name");

                try {
                    album.imageUrl = albums.getJSONObject(i).getJSONArray("image")
                            .getJSONObject(2).getString("#text");
                } catch(JSONException ex){
                    ex.printStackTrace();
                }

                album.artist = albums.getJSONObject(i).getString("artist");

                albumList.add(album);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return albumList;
    }
}
