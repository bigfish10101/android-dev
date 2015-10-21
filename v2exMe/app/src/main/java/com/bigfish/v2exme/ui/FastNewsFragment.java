package com.bigfish.v2exme.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigfish.v2exme.R;

import java.util.ArrayList;

import Models.V2exBaseModel;


public class FastNewsFragment extends Fragment implements INewsFragment {

    private View fragmentView;
    private ListView listView;
    private IFragmentTapListener iFragmentTapListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<?> fastList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        fragmentView = inflater.inflate(R.layout.fast_news_fragment, container, false);
        listView = (ListView)fragmentView.findViewById(R.id.fast_news_listview);
        if (listView != null) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (position < fastList.size()) {

                        if (iFragmentTapListener != null) {

                            iFragmentTapListener.tapFastNewsItem((V2exBaseModel)fastList.get(position));
                        }
                    }
                }
            });
        }

        swipeRefreshLayout = (SwipeRefreshLayout)fragmentView.findViewById(R.id.fast_news_pull_refresh_layout);

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if (iFragmentTapListener != null) {
                        iFragmentTapListener.refreshFastNews();
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

        fastList = list;

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
