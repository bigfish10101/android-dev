package com.bigfish.v2exme.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.support.v4.app.Fragment;

import com.bigfish.v2exme.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by shirley on 15/10/20.
 */
public class NewsAdapter extends BaseAdapter {

    private ArrayList<?> arrayList;
    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arrayList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (convertView == null) {

             listItemView = LayoutInflater.from(context).inflate(R.layout.hot_news_list_item, parent, false);
        }
        return listItemView;
    }

    public void setDataList(ArrayList<?> list) {
        arrayList = list;
    }
}
