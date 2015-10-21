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

import Models.V2exNodeListModel;

/**
 * Created by shirley on 15/10/20.
 */
public class NodesFragment extends Fragment implements INewsFragment {

    private View fragmentView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private IFragmentTapListener iFragmentTapListener;
    private ArrayList<?> nodesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        fragmentView = inflater.inflate(R.layout.node_fragment, container, false);
        listView = (ListView)fragmentView.findViewById(R.id.node_listview);
        if (listView != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (position < nodesList.size()) {

                        if (iFragmentTapListener != null) {

                            iFragmentTapListener.tapNodeNewsItem((V2exNodeListModel)nodesList.get(position));
                        }
                    }
                }
            });
        }

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

        nodesList = list;

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
