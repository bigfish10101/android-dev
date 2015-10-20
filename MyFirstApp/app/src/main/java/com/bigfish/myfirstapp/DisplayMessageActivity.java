package com.bigfish.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String editMessage = intent.getStringExtra(MyActivity.EXTRA_MSG);

        TextView textView = (TextView)findViewById(R.id.display_msg);
        if (textView != null) {
            textView.setText(editMessage);
        }

        SharedPreferences sharedPreferences =  getSharedPreferences("haha", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "hahah");
        Log.i("onCreate name -> ", name);
    }

}
