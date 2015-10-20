package com.bigfish.v2exme.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigfish.v2exme.R;

import java.util.ArrayList;

import Models.V2exBaseModel;


public class FastNewsFragment extends Fragment implements INewsFragment {

    private View fragmentView;
    private ListView listView;
    private IFragmentTapListener iFragmentTapListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        fragmentView = inflater.inflate(R.layout.fast_news_fragment, container, false);
        listView = (ListView)fragmentView.findViewById(R.id.fast_news_listview);
        return fragmentView;
    }

    public void reloadDatas(ArrayList<?> list) {

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
