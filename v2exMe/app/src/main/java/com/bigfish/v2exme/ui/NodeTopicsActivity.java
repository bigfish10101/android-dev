package com.bigfish.v2exme.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigfish.v2exme.R;
import com.google.gson.Gson;

import net.V2exNetwork;

import java.util.ArrayList;

import Models.V2exBaseModel;
import Models.V2exTopicModel;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class NodeTopicsActivity extends AppCompatActivity {

    private V2exNetwork v2exNetwork;
    private ACProgressFlower progressFlower;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String node_id;
    private ArrayList<?> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_topics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        v2exNetwork = new V2exNetwork(this);

        progressFlower = new ACProgressFlower.Builder(this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();

        listView = (ListView) findViewById(R.id.node_topic_list_view);
        if (listView != null) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (position < list.size()) {

                        V2exBaseModel model = (V2exBaseModel)list.get(position);
                        if (model != null) {

                            Intent intent = new Intent(getApplicationContext(), TopicInfoActivity.class);

                            Gson gson = new Gson();
                            String json = gson.toJson(model);
                            intent.putExtra(MainActivity.NODE_TOPIC_INFO_ACTIVITY_MODEL, json);

                            startActivity(intent);
                        }
                    }
                }
            });
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.node_topic_refresh_layout);
        if (swipeRefreshLayout != null) {

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    getNodeTopics(node_id);
                }
            });
        }

        Intent intent = getIntent();
        if (intent != null) {

            setTitle(intent.getStringExtra(MainActivity.NODE_TOPICS_ACTIVITY_TITLE));

            node_id = intent.getStringExtra(MainActivity.NODE_TOPICS_ACTIVITY_NODEID);

            progressFlower.show();
            getNodeTopics(node_id);
        }
    }


    public void getNodeTopics(String node_id) {

        v2exNetwork.getNodeTopics(node_id, new V2exNetwork.NodeTopicsListener() {
            @Override
            public void onSuccResponse(ArrayList<V2exTopicModel> responseList) {

                progressFlower.hide();
                swipeRefreshLayout.setRefreshing(false);

                list = responseList;

                NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext());
                newsAdapter.setDataList(responseList, new NewsAdapter.TapAdapterViewListener() {
                    @Override
                    public void tapUserName(V2exBaseModel model) {

                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra(MainActivity.PROFILE_ACTIVITY_USERNAME, model.memberModel.username);
                        startActivity(intent);
                    }
                });

                if (listView != null) {

                    listView.setAdapter(newsAdapter);
                }

            }

            @Override
            public void onFailResponse() {

                progressFlower.hide();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
