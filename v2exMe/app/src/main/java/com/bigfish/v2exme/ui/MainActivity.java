package com.bigfish.v2exme.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bigfish.v2exme.R;

import net.V2exNetwork;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private int viewPageIndex = -1;

    private V2exNetwork v2exNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v2exNetwork = new V2exNetwork(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // 设置Tabs
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setPageMargin(0);
        viewPager.setAdapter(viewPageAdapter);

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

        this.getNews(pageIndex);
    }

    // 获取网络数据
    public void getNews(int pageIndex) {

        if (pageIndex == 0) {

            v2exNetwork.getHotNews(new V2exNetwork.HotNewsListener() {
                @Override
                public void onSuccResponse(ArrayList<?> responseList) {

                }

                @Override
                public void onFailResponse() {

                }
            });
        } else {

            v2exNetwork.getFastNews(new V2exNetwork.FastNewsListener() {
                @Override
                public void onSuccResponse(ArrayList<?> responseList) {

                }

                @Override
                public void onFailResponse() {

                }
            });
        }
    }
}





































