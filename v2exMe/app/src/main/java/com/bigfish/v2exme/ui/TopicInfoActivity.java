package com.bigfish.v2exme.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bigfish.v2exme.R;
import com.google.gson.Gson;

import net.V2exNetwork;

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

        Intent intent = getIntent();
        Gson gson = new Gson();
        if (intent != null) {

            V2exBaseModel model = gson.fromJson(intent.getStringExtra(MainActivity.NODE_TOPIC_INFO_ACTIVITY_MODEL), V2exBaseModel.class);
            if (model != null) {

                v2exBaseModel = model;

                initUI(model);

                progressFlower.show();
                getReplies(model);
            }
        }
    }

    public void initUI(V2exBaseModel model) {

        if (model == null) return;
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
