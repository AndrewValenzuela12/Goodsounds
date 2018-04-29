package com.example.andrewvalenzuela.goodsounds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchEditText;
    private Button mListButton;
    private TextView mShakeTextView;
    private Context mContext;
    private ListView mListView;
    private AlbumAdapter mAdapter;
    private Album selectedAlbum;
    private View selectedItem;

    public String randomTitle;
    public String randomArtist;
    public String randomUrl;
    public String randomSimilarArtists;

    public ArrayList<Album> albumList = new ArrayList<>();

    public static final String API_KEY = "e1e823dc0969cf6407878c51165f40f5";


    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mSearchEditText = findViewById(R.id.searchEditText);
        mShakeTextView = findViewById(R.id.textView);


        // shake detector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            public void onShake() {
                Toast.makeText(MainActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
                mShakeTextView.setText("Searching...");
                fetchRandomArtist();
            }
        });

        mListButton = findViewById(R.id.homepage_myList_button);


        //albumList.add(new Album("hi", "https://lastfm-img2.akamaized.net/i/u/174s/7c9c4d1009514b178c82f2201e3a1fce.png","name"));
        mListView = findViewById(R.id.search_list_view);
        mAdapter = new AlbumAdapter(mContext, albumList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                selectedAlbum = albumList.get(position);
                selectedItem = mListView.getChildAt(position - mListView.getFirstVisiblePosition());
                // create my intent package
                // add all the information needed for detail page
                // startActivity with that intent

                // explicit
                Intent detailIntent = new Intent(mContext, AlbumDetailActivity.class);
                // put title and and instruction URL
                detailIntent.putExtra("album_title", selectedAlbum.title);
                detailIntent.putExtra("album_artist", selectedAlbum.artist);
                detailIntent.putExtra("album_url", selectedAlbum.imageUrl);
                detailIntent.putExtra("album_rating", selectedAlbum.rating);
                detailIntent.putExtra("album_comment", selectedAlbum.comment);
                detailIntent.putExtra("btn_name", "ADD");

                startActivity(detailIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }


    public void onClickMyList(View view) {
        startActivity(new Intent(MainActivity.this, MyListActivity.class));
    }


    public void onClickSearch(View view) {
        mShakeTextView.setText("Searching...");
        fetchResults(mSearchEditText.getText().toString());

    }

    public void fetchResults(String keyword) {
        //ArrayList<Album> fetchedList = new ArrayList<Album>();

        String url = "https://ws.audioscrobbler.com/2.0/?method=album.search&album="
                + keyword + "&api_key=" + API_KEY + "&format=json";


        RequestQueue queue = Volley.newRequestQueue(this);
        //mShakeTextView.setText("Processing...");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        //mShakeTextView.setText("Response: " + response.toString());
                        JSONArray array = null;
                        try{
                            array = response.getJSONObject("results")
                                    .getJSONObject("albummatches").getJSONArray("album");
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }

                        //mShakeTextView.setText("Response: " + albumList.toString());
                        albumList.clear();
                        albumList.addAll(Album.getAlbumList(array, mContext));

                        mShakeTextView.setText("... or shake for new music!");
                        ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        mShakeTextView.setText("Response: " + error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void fetchRandomArtist() {

        String url = "https://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=" + API_KEY + "&format=json";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray artistArray = null;

                        try{
                            artistArray = response.getJSONObject("artists")
                                    .getJSONArray("artist");
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }
                        int artistArrayLen = artistArray.length();
                        int randomPosition = randomNumber(artistArrayLen);

                        JSONObject randomArtistObj = null;
                        try{
                            randomArtistObj = artistArray.getJSONObject(randomPosition);
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }
                        String artistId = null;
                        try {
                            artistId = randomArtistObj.getString("mbid");
                            randomArtist = randomArtistObj.getString("name");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //mShakeTextView.setText(artistId);
                        fetchSimilarArtist(artistId);
                        //fetchRandomAlbum(artistId);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        mShakeTextView.setText("Response: " + error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void fetchSimilarArtist(final String artist_mbid) {

        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&mbid=" + artist_mbid +
                "&api_key=" + API_KEY + "&format=json";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray artistArray = null;
                        ArrayList<String> similarArtistsArray = new ArrayList<String>();

                        try{
                            artistArray = response.getJSONObject("similarartists")
                                    .getJSONArray("artist");
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }
                        for (int i = 0; i < 3; i++) {

                            try {
                                similarArtistsArray.add(artistArray.getJSONObject(i).getString("name"));
                            } catch(JSONException ex){
                                ex.printStackTrace();
                            }
                        }
                        randomSimilarArtists = android.text.TextUtils.join(", ", similarArtistsArray);
                        //mShakeTextView.setText(randomSimilarArtists);
                        fetchRandomAlbum(artist_mbid);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        mShakeTextView.setText("Response: " + error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void fetchRandomAlbum(String artist_mbid) {

        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&mbid="
                + artist_mbid + "&api_key=" + API_KEY + "&format=json";

        final RequestQueue queue = Volley.newRequestQueue(this);
        //mShakeTextView.setText("Processing...");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray array = null;

                        try{
                            array = response.getJSONObject("topalbums")
                                    .getJSONArray("album");
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }

                        int randomPosition = randomNumber(array.length());

                        //get random album from a random artist
                        JSONObject randomAlbum = null;

                        try{
                            randomAlbum = array.getJSONObject(randomPosition);
                            randomTitle = randomAlbum.getString("name");
                            randomUrl = randomAlbum.getJSONArray("image")
                                    .getJSONObject(2).getString("#text");
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }

                        //mShakeTextView.setText("Album: " + randomTitle);
                        startRandomAlbumActivity();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        mShakeTextView.setText("Response: " + error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void startRandomAlbumActivity() {
        // create my intent package
        // add all the information needed for detail page
        // startActivity with that intent

        // explicit
        Intent randomIntent = new Intent(mContext, RandomAlbumActivity.class);
        // put title and and instruction URL
        randomIntent.putExtra("album_title", randomTitle);
        randomIntent.putExtra("album_artist", randomArtist);
        randomIntent.putExtra("album_url", randomUrl);
        randomIntent.putExtra("similar", randomSimilarArtists);

        startActivity(randomIntent);
        mShakeTextView.setText("... or shake for new music!");
    }

    public int randomNumber(int max) {
        Random r = new Random();
        int Low = 0;
        int High = max;
        return r.nextInt(High-Low) + Low;
    }


}
