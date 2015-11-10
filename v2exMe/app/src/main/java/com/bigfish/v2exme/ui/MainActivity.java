package com.bigfish.v2exme.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigfish.v2exme.R;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import net.V2exNetwork;

import java.util.ArrayList;

import Models.V2exBaseModel;
import Models.V2exFastNewsModel;
import Models.V2exHotNewsModel;
import Models.V2exNodeListModel;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class MainActivity extends AppCompatActivity implements IFragmentTapListener {

    public final static String PROFILE_ACTIVITY_USERNAME = "com.profile.activity.username";

    public final static String NODE_TOPICS_ACTIVITY_NODEID = "com.node.topics.activity.node_id";
    public final static String NODE_TOPICS_ACTIVITY_TITLE = "com.node.topics.activity.title";
    public final static String NODE_TOPIC_INFO_ACTIVITY_MODEL = "com.node.topic.info.activity.model";

    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private int viewPageIndex = -1;

    private V2exNetwork v2exNetwork;
    private ACProgressFlower progressFlower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v2exNetwork = new V2exNetwork(this);
        progressFlower = new ACProgressFlower.Builder(this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 设置Tabs
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setPageMargin(0);
        viewPager.setAdapter(viewPageAdapter);
        viewPageAdapter.addFragmentTapListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                movePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        movePage(0);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(this).setTitle("是否退出V2exMe?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.menu_main, menu);
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

            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
    }

    // 点击最热新闻
    public void tapHotNews(View v) {

        this.movePage(0);
        viewPager.setCurrentItem(0);
    }

    // 点击最新新闻
    public void tapFastNews(View v) {

        this.movePage(1);
        viewPager.setCurrentItem(1);
    }

    // 点击节点
    public void tapNodeNews(View v) {

        Log.i("tapNodeNews", "tapNode.news...");
        this.movePage(2);
        viewPager.setCurrentItem(2);
    }

    // 切换页面
    public void movePage(int pageIndex) {

        if (viewPageIndex == pageIndex) return;

        TextView hotNewsTextView = (TextView)findViewById(R.id.hotnews);
        TextView fastNewsTextView = (TextView)findViewById(R.id.fastnews);
        TextView nodeTextView = (TextView)findViewById(R.id.nodenews);

        viewPageIndex = pageIndex;

        if (pageIndex == 0) {   // 获取最热新闻

            hotNewsTextView.setTextColor(getResources().getColor(R.color.selColor));
            fastNewsTextView.setTextColor(getResources().getColor(R.color.unSelColor));
            nodeTextView.setTextColor(getResources().getColor(R.color.unSelColor));
        } else if (pageIndex == 1) {                // 获取最新新闻

            hotNewsTextView.setTextColor(getResources().getColor(R.color.unSelColor));
            fastNewsTextView.setTextColor(getResources().getColor(R.color.selColor));
            nodeTextView.setTextColor(getResources().getColor(R.color.unSelColor));
        } else if (pageIndex == 2) {

            hotNewsTextView.setTextColor(getResources().getColor(R.color.unSelColor));
            fastNewsTextView.setTextColor(getResources().getColor(R.color.unSelColor));
            nodeTextView.setTextColor(getResources().getColor(R.color.selColor));
        }

        progressFlower.show();
        this.getNews(pageIndex);
    }

    // 获取网络数据
    public void getNews(int pageIndex) {

        if (pageIndex == 0) {

            v2exNetwork.getHotNews(new V2exNetwork.HotNewsListener() {
                @Override
                public void onSuccResponse(ArrayList<V2exHotNewsModel> responseList) {

                    Log.i("getHotNews onSucc", String.valueOf(responseList.size()));
                    progressFlower.hide();

                    INewsFragment iNewsFragment = viewPageAdapter.hotINewsFragment();
                    if (iNewsFragment != null) {
                        iNewsFragment.reloadDatas(responseList);
                    }
                }

                @Override
                public void onFailResponse() {

                    Log.i("getHotNews onFail", "Error!");
                    progressFlower.hide();
                }
            });
        } else if (pageIndex == 1) {

            v2exNetwork.getFastNews(new V2exNetwork.FastNewsListener() {
                @Override
                public void onSuccResponse(ArrayList<V2exFastNewsModel> responseList) {

                    Log.i("getFastNews onSucc", String.valueOf(responseList.size()));
                    progressFlower.hide();

                    INewsFragment iNewsFragment = viewPageAdapter.fastINewsFragment();
                    if (iNewsFragment != null) {
                        iNewsFragment.reloadDatas(responseList);
                    }
                }

                @Override
                public void onFailResponse() {

                    Log.i("getFastNews onFail", "Error!");
                    progressFlower.hide();
                }
            });
        } else {

            v2exNetwork.getNodeNews(new V2exNetwork.NodeNewsListener() {
                @Override
                public void onSuccResponse(ArrayList<V2exNodeListModel> responseList) {

                    progressFlower.hide();

                    INewsFragment iNewsFragment = viewPageAdapter.nodeINewsFrgment();
                    if (iNewsFragment != null) {
                        iNewsFragment.reloadDatas(responseList);
                    }
                }

                @Override
                public void onFailResponse() {

                    progressFlower.hide();
                }
            });
        }
    }

    public void tapFragmentListUserName(V2exBaseModel model) {

        if (model == null) return;

        Log.i("tap", model.memberModel.username);

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(PROFILE_ACTIVITY_USERNAME, model.memberModel.username);
        startActivity(intent);
    }

    public void refreshHotNews() {

        getNews(0);
    }

    public void refreshFastNews() {

        getNews(1);
    }

    public void refreshNodeNews() {

        getNews(2);
    }

    public void tapHotNewsItem(V2exBaseModel model) {

        if (model == null) return;

        Intent intent = new Intent(this, TopicInfoActivity.class);

        Gson gson = new Gson();
        String json = gson.toJson(model);
        intent.putExtra(MainActivity.NODE_TOPIC_INFO_ACTIVITY_MODEL, json);

        startActivity(intent);
    }

    public void tapFastNewsItem(V2exBaseModel model) {

        if (model == null) return;

        Intent intent = new Intent(this, TopicInfoActivity.class);

        Gson gson = new Gson();
        String json = gson.toJson(model);
        intent.putExtra(MainActivity.NODE_TOPIC_INFO_ACTIVITY_MODEL, json);

        startActivity(intent);
    }

    public void tapNodeNewsItem(V2exNodeListModel model) {

        if (model == null) return;

        Intent intent = new Intent(this, NodeTopicsActivity.class);
        intent.putExtra(NODE_TOPICS_ACTIVITY_NODEID, model.iid);
        intent.putExtra(NODE_TOPICS_ACTIVITY_TITLE, model.title);
        startActivity(intent);
    }
}





































