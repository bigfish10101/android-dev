package com.bigfish.v2exme.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bigfish.v2exme.R;

import net.V2exNetwork;

import java.util.ArrayList;

import Models.V2exTopicModel;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class NodeTopicsActivity extends AppCompatActivity {

    private V2exNetwork v2exNetwork;
    private ACProgressFlower progressFlower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_topics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        v2exNetwork = new V2exNetwork(this);

        progressFlower = new ACProgressFlower.Builder(this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();

        Intent intent = getIntent();
        if (intent != null) {

            setTitle(intent.getStringExtra(MainActivity.NODE_TOPICS_ACTIVITY_TITLE));

            String node_id = intent.getStringExtra(MainActivity.NODE_TOPICS_ACTIVITY_NODEID);
            getNodeTopics(node_id);
        }
    }


    public void getNodeTopics(String node_id) {

        progressFlower.show();
        v2exNetwork.getNodeTopics(node_id, new V2exNetwork.NodeTopicsListener() {
            @Override
            public void onSuccResponse(ArrayList<V2exTopicModel> responseList) {

                progressFlower.hide();
            }

            @Override
            public void onFailResponse() {

                progressFlower.hide();
            }
        });
    }

}
