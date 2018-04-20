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

public class MainActivity extends AppCompatActivity {

    private EditText mSearchEditText;
    private Button mSearchButton;
    private TextView mShakeTextView;
    private Context mContext;
    private ListView mListView;
    private AlbumAdapter mAdapter;
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

                // whatever we want to do when we detect a shake

            }
        });

        //put search results into ListView
        mListView = findViewById(R.id.search_list_view);
        mAdapter = new AlbumAdapter(mContext, albumList);
        mListView.setAdapter(mAdapter);
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

    public void onClickSearch(View view) {
        mShakeTextView.setText("Processing...");
        fetchResults(mSearchEditText.getText().toString());

    }

    public void fetchResults(String keyword) {
        //ArrayList<Album> fetchedList = new ArrayList<Album>();

        String url = "https://ws.audioscrobbler.com/2.0/?method=album.search&album="
                + keyword + "&api_key=" + API_KEY + "&format=json";

        //String url = "https://ws.audioscrobbler.com/2.0/?method=album.search&album=tame&api_key=57468c7603262db4ad67f48474468b8e&format=json";

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

                       // mShakeTextView.setText("Response: " + albumList.toString());
                        albumList = Album.getAlbumList(array, mContext);
                        //mAdapter = new AlbumAdapter(mContext, albumList);
                        mShakeTextView.setText("Size: " + albumList.size());
                        mAdapter.notifyDataSetChanged();
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

}
