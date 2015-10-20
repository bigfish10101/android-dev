package com.bigfish.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.widget.EditText;

import android.net.Uri;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.List;


public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MSG = "com.bigfish.myfirstapp.extramessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // read Preferences key-value data

        SharedPreferences sharedPreferences = getSharedPreferences("haha", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "yangjie");
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // interface actions function
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String editMessage = editText.getText().toString();
        intent.putExtra(EXTRA_MSG, editMessage);
        startActivity(intent);
    }

    public void goFragment(View v) {
        Intent intent = new Intent(this, MainFragmentActivity.class);
        startActivity(intent);
    }

    public void shareApp(View v) {

        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

// Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

// Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(mapIntent);
        } else {
            Log.i("hahha", "没有地图可以选择!");
        }
    }

    public void callSomeone(View v) {

        //Uri number = Uri.parse("tel:18681485331");
       // Intent callIntent = new Intent(Intent.ACTION_CALL, number);
       // startActivity(callIntent);

        Uri webpage = Uri.parse("http://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    public void httpGet(View v) {

    }
}
