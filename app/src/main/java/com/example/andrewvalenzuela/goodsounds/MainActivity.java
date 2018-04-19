package com.example.andrewvalenzuela.goodsounds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchEditText;
    private Button mSearchButton;
    private TextView mShakeTextView;
    private Context mContext;

    public static final String API_KEY = "e1e823dc0969cf6407878c51165f40f5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mSearchEditText = findViewById(R.id.searchEditText);
        mSearchButton = findViewById(R.id.searchButton);
        mShakeTextView = findViewById(R.id.textView);
    }

    public void onClickSearch(View view) {
        fetchResults(mSearchEditText.getText().toString());
    }

    public void fetchResults(String keyword) {
        //ArrayList<Album> fetchedList = new ArrayList<Album>();

        String url = "https://ws.audioscrobbler.com/2.0/?method=album.search&album="
                + keyword + "&api_key=" + API_KEY + "&format=json";

        //String url = "https://ws.audioscrobbler.com/2.0/?method=album.search&album=tame&api_key=57468c7603262db4ad67f48474468b8e&format=json";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        
                        mShakeTextView.setText("Response: " + response.toString());
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
