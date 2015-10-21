package com.bigfish.v2exme.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigfish.v2exme.R;

import net.V2exNetwork;

import java.util.ArrayList;

import Models.V2exBaseModel;
import Models.V2exFastNewsModel;
import Models.V2exHotNewsModel;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class MainActivity extends AppCompatActivity implements IFragmentTapListener {

    public final static String PROFILE_ACTIVITY_USERNAME = "com.profile.activity.username";

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

    // 切换页面
    public void movePage(int pageIndex) {

        if (viewPageIndex == pageIndex) return;

        TextView hotNewsTextView = (TextView)findViewById(R.id.hotnews);
        TextView fastNewsTextView = (TextView)findViewById(R.id.fastnews);

        viewPageIndex = pageIndex;

        if (pageIndex == 0) {   // 获取最热新闻

            hotNewsTextView.setTextColor(getResources().getColor(R.color.selColor));
            fastNewsTextView.setTextColor(getResources().getColor(R.color.unSelColor));
        } else {                // 获取最新新闻

            hotNewsTextView.setTextColor(getResources().getColor(R.color.unSelColor));
            fastNewsTextView.setTextColor(getResources().getColor(R.color.selColor));
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
        } else {

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
}





































