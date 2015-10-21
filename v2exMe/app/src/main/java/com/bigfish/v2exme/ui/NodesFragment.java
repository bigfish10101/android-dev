package com.bigfish.v2exme.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigfish.v2exme.R;

import java.util.ArrayList;

/**
 * Created by shirley on 15/10/20.
 */
public class NodesFragment extends Fragment implements INewsFragment {

    private View fragmentView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private IFragmentTapListener iFragmentTapListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        fragmentView = inflater.inflate(R.layout.node_fragment, container, false);
        listView = (ListView)fragmentView.findViewById(R.id.node_listview);
        swipeRefreshLayout = (SwipeRefreshLayout)fragmentView.findViewById(R.id.node_news_pull_refresh_layout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if (iFragmentTapListener != null) {
                        iFragmentTapListener.refreshNodeNews();
                    }
                }
            });
        }
        return fragmentView;
    }

    @Override
    public void reloadDatas(ArrayList<?> list) {

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }

        NodesAdapter nodesAdapter = new NodesAdapter(getActivity().getApplicationContext());
        nodesAdapter.setDateList(list);

        if (listView != null) {

            listView.setAdapter(nodesAdapter);
        }
    }

    public void addFragmentTapListener(IFragmentTapListener iFragmentTapListener) {
        this.iFragmentTapListener = iFragmentTapListener;
    }
}
