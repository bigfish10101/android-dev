package com.bigfish.v2exme.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.Image;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bigfish.v2exme.R;

import Models.V2exBaseModel;
import Models.V2exTopicReplyModel;

/**
 * Created by shirley on 15/10/24.
 */
public class NodeTopicInfoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<?> list;
    private V2exBaseModel v2exBaseModel;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private LruCache<String, Bitmap> imageCache;

    public NodeTopicInfoAdapter(Context context, V2exBaseModel v2exBaseModel) {

        this.context = context;
        this.v2exBaseModel = v2exBaseModel;

        requestQueue = Volley.newRequestQueue(context);

        imageCache = new LruCache<String, Bitmap>(1000);

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return imageCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                imageCache.put(url, bitmap);
            }
        });
    }

    @Override
    public int getCount() {
        return 2 + list.size() ;
    }

    @Override
    public Object getItem(int position) {
        return String.valueOf(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {

            if (getItemViewType(position) == 0) {

                listItemView = LayoutInflater.from(context).inflate(R.layout.content_topic_head_item, parent, false);
            } else if (getItemViewType(position) == 1) {

                listItemView = LayoutInflater.from(context).inflate(R.layout.content_topic_content_info, parent, false);
            } else if (getItemViewType(position) == 2) {

                listItemView = LayoutInflater.from(context).inflate(R.layout.content_topic_reply, parent, false);
            }
        }

        if (getItemViewType(position) == 0) {

            TextView nodeTopicTitleTextView = (TextView) listItemView.findViewById(R.id.topic_info_title_text_view);
            if (nodeTopicTitleTextView != null) {

                nodeTopicTitleTextView.setTypeface(null, Typeface.BOLD);
                nodeTopicTitleTextView.setText(v2exBaseModel.title);
            }

            NetworkImageView networkImageView = (NetworkImageView) listItemView.findViewById(R.id.topic_avatar_image_view);
            if (networkImageView != null) {

                networkImageView.setImageUrl("https:" + v2exBaseModel.memberModel.avatar_large, imageLoader);
            }

            TextView topicInfoNodeNameTextView = (TextView) listItemView.findViewById(R.id.topic_info_node_name_text_view);
            if (topicInfoNodeNameTextView != null) {

                topicInfoNodeNameTextView.setText(v2exBaseModel.nodeModel.title);
                topicInfoNodeNameTextView.setTypeface(null, Typeface.BOLD);
            }

            TextView topicInfoRepliesNumTextView = (TextView) listItemView.findViewById(R.id.topic_info_replies_num);
            if (topicInfoRepliesNumTextView != null) {
                topicInfoRepliesNumTextView.setText(v2exBaseModel.replies);
            }

            TextView topicInfoUserNameTextView = (TextView) listItemView.findViewById(R.id.topic_info_user_name_title);
            if (topicInfoUserNameTextView != null) {
                topicInfoUserNameTextView.setTypeface(null, Typeface.BOLD);
                topicInfoUserNameTextView.setText("@" + v2exBaseModel.memberModel.username);
            }
        } else if (getItemViewType(position) == 1) {

            TextView contentInfoTextView = (TextView) listItemView.findViewById(R.id.topic_info_content_info_text_view);
            if (contentInfoTextView != null) {

                contentInfoTextView.setText(v2exBaseModel.content);
            }
        } else if (getItemViewType(position) == 2) {

            int index = position - 2;
            V2exTopicReplyModel v2exTopicReplyModel = (V2exTopicReplyModel)list.get(index);
            if (v2exTopicReplyModel != null) {

                NetworkImageView networkImageView = (NetworkImageView) listItemView.findViewById(R.id.content_topic_reply_avatar_image_view);
                if (networkImageView != null) {
                    networkImageView.setImageUrl("https:" + v2exTopicReplyModel.memberModel.avatar_large, imageLoader);
                }

                TextView userNameTextView = (TextView) listItemView.findViewById(R.id.content_topic_reply_user_name_text_view);
                if (userNameTextView != null) {
                    userNameTextView.setText("@" + v2exTopicReplyModel.memberModel.username);
                    userNameTextView.setTypeface(null, Typeface.BOLD);
                }

                TextView contentTextView = (TextView) listItemView.findViewById(R.id.content_topic_reply_content_text_view);
                if (contentTextView != null) {

                    contentTextView.setText(v2exTopicReplyModel.content);
                }
            }
        }

        return listItemView;
    }

    public void setDataList(ArrayList<?> list) {

        this.list = list;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) return 0;
        else if (position == 1) return 1;
        return 2;
    }
}
