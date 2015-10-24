package com.bigfish.v2exme.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bigfish.v2exme.R;
import com.google.gson.Gson;

import net.V2exNetwork;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Models.V2exBaseModel;
import Models.V2exTopicReplyModel;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class TopicInfoActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private V2exBaseModel v2exBaseModel;
    private V2exNetwork v2exNetwork;
    private ACProgressFlower progressFlower;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private LruCache<String, Bitmap> imageCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.topic_info_refresh_layout);
        if (swipeRefreshLayout != null) {

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if (v2exBaseModel != null) {

                        getReplies(v2exBaseModel);
                    } else {

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }

        v2exNetwork = new V2exNetwork(this);

        progressFlower = new ACProgressFlower.Builder(this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();

        requestQueue = Volley.newRequestQueue(this);
        imageCache = new LruCache<String, Bitmap>(1000);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            @Override
            public Bitmap getBitmap(String url) {
                return imageCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                imageCache.put(url, bitmap);
            }
        });

        Intent intent = getIntent();
        Gson gson = new Gson();
        if (intent != null) {

            V2exBaseModel model = gson.fromJson(intent.getStringExtra(MainActivity.NODE_TOPIC_INFO_ACTIVITY_MODEL), V2exBaseModel.class);
            if (model != null) {

                v2exBaseModel = model;

                progressFlower.show();
                getReplies(model);
            }
        }
    }

    public void getReplies(V2exBaseModel model) {

        if (model == null) return;

        v2exNetwork.getTopicReplies(model.iid, new V2exNetwork.TopicRepliesListener() {
            @Override
            public void onSuccResponse(ArrayList<V2exTopicReplyModel> responseList) {

                if (swipeRefreshLayout != null) {

                    swipeRefreshLayout.setRefreshing(false);
                }

                progressFlower.hide();

                NodeTopicInfoAdapter nodeTopicInfoAdapter = new NodeTopicInfoAdapter(getApplicationContext(), v2exBaseModel);
                nodeTopicInfoAdapter.setDataList(responseList);
                ListView listView = (ListView) findViewById(R.id.topic_info_list_view);
                if (listView != null) {

                    listView.setAdapter(nodeTopicInfoAdapter);
                }
            }

            @Override
            public void onFailResponse() {

                if (swipeRefreshLayout != null) {

                    swipeRefreshLayout.setRefreshing(false);
                }

                progressFlower.hide();
            }
        });
    }
}
