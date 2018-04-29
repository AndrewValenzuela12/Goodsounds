package com.example.andrewvalenzuela.goodsounds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by AndrewValenzuela on 4/28/18.
 */

public class MyListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_mylist);
    }

    public void onClickBackHome(View view){this.finish();}
}