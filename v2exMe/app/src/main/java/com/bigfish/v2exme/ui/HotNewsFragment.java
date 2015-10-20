package com.bigfish.v2exme.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bigfish.v2exme.R;

import java.util.ArrayList;

public class HotNewsFragment extends Fragment implements INewsFragment {

    private View fragmentView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        fragmentView = inflater.inflate(R.layout.hot_news_fragment, container, false);
        listView = (ListView)fragmentView.findViewById(R.id.hot_news_listview);
        return fragmentView;
    }

    public void reloadDatas(ArrayList<?> list) {

        NewsAdapter newsAdapter = new NewsAdapter(getActivity().getApplicationContext());
        newsAdapter.setDataList(list);
        listView.setAdapter(newsAdapter);
    }
}