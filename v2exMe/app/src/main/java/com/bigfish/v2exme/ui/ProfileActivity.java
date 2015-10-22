package com.bigfish.v2exme.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bigfish.v2exme.R;

import net.V2exNetwork;

import Models.V2exProfileModel;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class ProfileActivity extends AppCompatActivity {

    private ACProgressFlower progressFlower;
    private V2exNetwork v2exNetwork;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressFlower = new ACProgressFlower.Builder(this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();
        v2exNetwork = new V2exNetwork(this);

        init();

        Intent intent = getIntent();
        if (intent != null) {

            setTitle(intent.getStringExtra(MainActivity.PROFILE_ACTIVITY_USERNAME));

            loadingProfile(intent.getStringExtra(MainActivity.PROFILE_ACTIVITY_USERNAME));

        } else {

            setTitle("v2exMe");
        }

    }

    public void init() {

        requestQueue = Volley.newRequestQueue(this);

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> imgCache = new LruCache<String, Bitmap>(1000);
            @Override
            public Bitmap getBitmap(String url) {
                return imgCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                imgCache.put(url, bitmap);
            }
        });
    }

    public void loadingProfile(String username) {

        progressFlower.show();

        v2exNetwork.getProfile(username, new V2exNetwork.ProfileListener() {
            @Override
            public void onSuccResponse(V2exProfileModel model) {

                progressFlower.hide();

                // refresh UI
                refreshUI(model);
            }

            @Override
            public void onFailResponse() {

                progressFlower.hide();
            }
        });
    }

    public void refreshUI(V2exProfileModel model) {

        if (model == null) return;


        TextView nameTextView = (TextView) findViewById(R.id.profile_name_textview);
        if (nameTextView != null) {

            nameTextView.setText(model.username);
        }

        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.profile_avatar_image_view);
        if (imageView != null) {

            imageView.setImageUrl("https:" + model.avatar_large, imageLoader);
        }

        TextView taglieTextView = (TextView) findViewById(R.id.profile_taglie_textview);
        if (taglieTextView != null) {

            taglieTextView.setText(model.tagline);
        }

        TextView locationTextView = (TextView) findViewById(R.id.profile_location_textview);
        if (locationTextView != null) {

            locationTextView.setText("居住地: " + model.location);
        }

        TextView websiteTextView = (TextView) findViewById(R.id.profile_website_textview);
        if (websiteTextView != null) {

            websiteTextView.setText("个人网站: " + model.website);
        }

        TextView githubTextView = (TextView) findViewById(R.id.profile_github_textview);
        if (githubTextView != null) {

            githubTextView.setText("github: " + model.github);
        }

        TextView twitterTextView = (TextView) findViewById(R.id.profile_twitter_textview);
        if (twitterTextView != null) {

            twitterTextView.setText("推特账号: " + ((model.twitter.length() > 0) ? "@" + model.twitter : ""));
        }
    }
}
