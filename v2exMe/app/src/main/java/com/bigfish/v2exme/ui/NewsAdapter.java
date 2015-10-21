package com.bigfish.v2exme.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bigfish.v2exme.R;

import java.util.ArrayList;

import com.android.volley.*;

import Models.*;

/**
 * Created by shirley on 15/10/20.
 */
public class NewsAdapter extends BaseAdapter {

    public interface TapAdapterViewListener {
        public void tapUserName(V2exBaseModel model);
    }

    private ArrayList<?> arrayList;
    private Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private TapAdapterViewListener tapAdapterViewListener;

    public NewsAdapter(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> imgCache = new LruCache<String, Bitmap>(1000);
            @Override
            public Bitmap getBitmap(String url) {
                return imgCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                imgCache.put(url, bitmap);
            }
        });
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

        final V2exBaseModel v2exBaseModel = (V2exBaseModel)arrayList.get(arg0);

        if (v2exBaseModel != null) {

            NetworkImageView avatarImageView = (NetworkImageView) listItemView.findViewById(R.id.avatar_image_view);
            if (avatarImageView != null) {

                avatarImageView.setImageUrl("https:" + v2exBaseModel.memberModel.avatar_large, imageLoader);
                avatarImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tapAdapterViewListener != null) {
                            tapAdapterViewListener.tapUserName(v2exBaseModel);
                        }
                    }
                });
            }

            TextView titleTextView = (TextView) listItemView.findViewById(R.id.list_item_title);
            if (titleTextView != null) {
                titleTextView.setText(v2exBaseModel.title);
            }

            TextView nodeNameTextView = (TextView) listItemView.findViewById(R.id.node_name);
            if (nodeNameTextView != null) {
                nodeNameTextView.setText(v2exBaseModel.nodeModel.title);
            }

            final TextView userNameTextView = (TextView) listItemView.findViewById(R.id.user_name_title);
            if (userNameTextView != null) {
                userNameTextView.setText("@"+v2exBaseModel.memberModel.username);
                userNameTextView.setTypeface(null, Typeface.BOLD);

                userNameTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("click user name", v2exBaseModel.memberModel.username);

                        if (tapAdapterViewListener != null) {
                            tapAdapterViewListener.tapUserName(v2exBaseModel);
                        }
                    }
                });
            }

            TextView repliesTextView = (TextView) listItemView.findViewById(R.id.replies_num);
            if (repliesTextView != null) {
                repliesTextView.setText(v2exBaseModel.replies);
                repliesTextView.setTypeface(null, Typeface.BOLD);
            }
        }

        return listItemView;
    }

    public void setDataList(ArrayList<?> list, TapAdapterViewListener tapAdapterViewListener) {
        arrayList = list;
        this.tapAdapterViewListener = tapAdapterViewListener;
    }
}
