package com.bigfish.v2exme.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Log;

import com.bigfish.v2exme.R;

import java.util.ArrayList;

import Models.V2exBaseModel;

public class HotNewsFragment extends Fragment implements INewsFragment {

    private View fragmentView;
    private ListView listView;
    private IFragmentTapListener iFragmentTapListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<?> hotList;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        fragmentView = inflater.inflate(R.layout.hot_news_fragment, container, false);
        listView = (ListView)fragmentView.findViewById(R.id.hot_news_listview);

        if (listView != null) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.i("onItemClickListener ", String.valueOf(position));

                    if (position < hotList.size()) {

                        if (iFragmentTapListener != null) {

                            iFragmentTapListener.tapHotNewsItem((V2exBaseModel)hotList.get(position));
                        }
                    }
                }
            });
        }

        swipeRefreshLayout = (SwipeRefreshLayout)fragmentView.findViewById(R.id.hot_news_pull_refresh_layout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if (iFragmentTapListener != null) {
                        iFragmentTapListener.refreshHotNews();
                    }
                    }
            });
        }
        return fragmentView;
    }

    public void reloadDatas(ArrayList<?> list) {

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }

        hotList = list;

        NewsAdapter newsAdapter = new NewsAdapter(getActivity().getApplicationContext());
        newsAdapter.setDataList(list, new NewsAdapter.TapAdapterViewListener() {
            @Override
            public void tapUserName(V2exBaseModel model) {

                if (iFragmentTapListener != null) {
                    iFragmentTapListener.tapFragmentListUserName(model);
                }
            }
        });
        listView.setAdapter(newsAdapter);
    }

    public void addFragmentTapListener(IFragmentTapListener iFragmentTapListener) {
        this.iFragmentTapListener = iFragmentTapListener;
    }
}