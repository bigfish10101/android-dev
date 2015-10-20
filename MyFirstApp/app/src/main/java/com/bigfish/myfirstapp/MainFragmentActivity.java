package com.bigfish.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.String;
import java.io.File;

public class MainFragmentActivity extends FragmentActivity implements FirstFragment.OnFirstFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        if (findViewById(R.id.fragment_container) != null) {


            FirstFragment firstFragment = new FirstFragment();
            firstFragment.setFragmentParentActivity(this);

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

        }
    }

    public void moveToSecondFragment(View v) {

        Log.i("asasdasd", "moveToSecondFragment.....");

        SecondFragment secondFragment = new SecondFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, secondFragment).commit();
    }

    public void onReplaceSecondFragment() {

        this.moveToSecondFragment(null);

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = file.getPath();
        Log.i("pictures path", path);
    }
}
