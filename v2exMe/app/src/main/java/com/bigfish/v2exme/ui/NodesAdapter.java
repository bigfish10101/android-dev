package com.bigfish.v2exme.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Models.V2exNodeListModel;

import com.bigfish.v2exme.R;

import org.w3c.dom.Text;

/**
 * Created by shirley on 15/10/21.
 */
public class NodesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<?> arrayList;

    public NodesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.node_list_item, parent, false);
        }

        V2exNodeListModel model = (V2exNodeListModel)arrayList.get(position);
        if (model != null) {

            TextView titleTextView = (TextView) listItemView.findViewById(R.id.node_list_title);
            if (titleTextView != null) {
                titleTextView.setText(model.title);
                titleTextView.setTypeface(null, Typeface.BOLD);
            }

            TextView contentTextView = (TextView) listItemView.findViewById(R.id.node_list_content);
            if (contentTextView != null) {
                contentTextView.setText(model.header);

                if (model.header == null) {

                    if (model.footer != null) {
                        contentTextView.setText(model.footer);
                    } else {

                        contentTextView.setText("");
                    }
                }
            }
        }

        return listItemView;
    }

    public void setDateList(ArrayList<?> list) {
        arrayList = list;
    }
}
