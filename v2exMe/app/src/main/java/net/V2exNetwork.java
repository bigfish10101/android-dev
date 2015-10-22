package net;

import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.*;


public class V2exNetwork {

    public static String hotnews_url = "https://www.v2ex.com/api/topics/hot.json";
    public static String fastnews_url = "https://www.v2ex.com/api/topics/latest.json";
    public static String profile_url = "https://www.v2ex.com/api/members/show.json";
    public static String all_node_url = "http://www.v2ex.com/api/nodes/all.json";
    public static String replies_url = "http://www.v2ex.com/api/replies/show.json";
    public static String node_topics_url = "http://www.v2ex.com/api/topics/show.json";

    public interface HotNewsListener {
        void onSuccResponse(ArrayList<V2exHotNewsModel> responseList);
        void onFailResponse();
    }

    public interface FastNewsListener {
        void onSuccResponse(ArrayList<V2exFastNewsModel> responseList);
        void onFailResponse();
    }

    public interface ProfileListener {
        void onSuccResponse(V2exProfileModel model);
        void onFailResponse();
    }

    public interface NodeNewsListener {
        void onSuccResponse(ArrayList<V2exNodeListModel> responseList);
        void onFailResponse();
    }

    public interface NodeTopicsListener {
        void onSuccResponse(ArrayList<V2exTopicModel> responseList);
        void onFailResponse();
    }

    public interface TopicRepliesListener {
        void onSuccResponse(ArrayList<V2exTopicReplyModel> responseList);
        void onFailResponse();
    }


    private RequestQueue requestQueue;

    public V2exNetwork(Context context) {

        requestQueue = Volley.newRequestQueue(context);
    }

    public void getHotNews(final HotNewsListener callback) {

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, hotnews_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("getHotNews ", response.toString());

                ArrayList<V2exHotNewsModel> list = new ArrayList<V2exHotNewsModel>();
                for (int i = 0; i < response.length(); i ++) {
                   try {
                       JSONObject obj = response.getJSONObject(i);
                       if (obj != null) {
                           V2exHotNewsModel model = new V2exHotNewsModel();
                           model.iid = obj.getString("id");
                           model.title = obj.getString("title");
                           model.url = obj.getString("url");
                           model.content = obj.getString("content");
                           model.content_rendered = obj.getString("content_rendered");
                           model.replies = obj.getString("replies");
                           model.created = obj.getString("created");
                           model.last_modified = obj.getString("last_modified");
                           model.last_touched = obj.getString("last_touched");

                           V2exMemberModel memberModel = new V2exMemberModel();
                           JSONObject memberObj = obj.getJSONObject("member");
                           if (memberObj != null) {
                               memberModel.iid = memberObj.getString("id");
                               memberModel.username = memberObj.getString("username");
                               memberModel.tagline = memberObj.getString("tagline");
                               memberModel.avatar_mini = memberObj.getString("avatar_mini");
                               memberModel.avatar_normal = memberObj.getString("avatar_normal");
                               memberModel.avatar_large = memberObj.getString("avatar_large");
                           }
                           model.memberModel = memberModel;

                           V2exNodeModel nodeModel = new V2exNodeModel();
                           JSONObject nodeObj = obj.getJSONObject("node");
                           if (nodeObj != null) {
                               nodeModel.iid = nodeObj.getString("id");
                               nodeModel.vname = nodeObj.getString("name");
                               nodeModel.title = nodeObj.getString("title");
                               nodeModel.title_alternative = nodeObj.getString("title_alternative");
                               nodeModel.url = nodeObj.getString("url");
                               nodeModel.topics = nodeObj.getString("topics");
                               nodeModel.avatar_mini = nodeObj.getString("avatar_mini");
                               nodeModel.avatar_large = nodeObj.getString("avatar_large");
                               nodeModel.avatar_normal = nodeObj.getString("avatar_normal");
                           }
                           model.nodeModel = nodeModel;

                           list.add(model);
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                }

                callback.onSuccResponse(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("getHotNews", "error " + error.toString());

                callback.onFailResponse();
            }
        });

        requestQueue.add(request);
    }

    public void getFastNews(final FastNewsListener callback) {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, fastnews_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("getFastNews", response.toString());

                ArrayList<V2exFastNewsModel> list = new ArrayList<V2exFastNewsModel>();
                for (int i = 0; i < response.length(); i ++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if (obj != null) {
                            V2exFastNewsModel model = new V2exFastNewsModel();
                            model.iid = obj.getString("id");
                            model.title = obj.getString("title");
                            model.url = obj.getString("url");
                            model.content = obj.getString("content");
                            model.content_rendered = obj.getString("content_rendered");
                            model.replies = obj.getString("replies");
                            model.created = obj.getString("created");
                            model.last_modified = obj.getString("last_modified");
                            model.last_touched = obj.getString("last_touched");

                            V2exMemberModel memberModel = new V2exMemberModel();
                            JSONObject memberObj = obj.getJSONObject("member");
                            if (memberObj != null) {
                                memberModel.iid = memberObj.getString("id");
                                memberModel.username = memberObj.getString("username");
                                memberModel.tagline = memberObj.getString("tagline");
                                memberModel.avatar_mini = memberObj.getString("avatar_mini");
                                memberModel.avatar_normal = memberObj.getString("avatar_normal");
                                memberModel.avatar_large = memberObj.getString("avatar_large");
                            }
                            model.memberModel = memberModel;

                            V2exNodeModel nodeModel = new V2exNodeModel();
                            JSONObject nodeObj = obj.getJSONObject("node");
                            if (nodeObj != null) {
                                nodeModel.iid = nodeObj.getString("id");
                                nodeModel.vname = nodeObj.getString("name");
                                nodeModel.title = nodeObj.getString("title");
                                nodeModel.title_alternative = nodeObj.getString("title_alternative");
                                nodeModel.url = nodeObj.getString("url");
                                nodeModel.topics = nodeObj.getString("topics");
                                nodeModel.avatar_mini = nodeObj.getString("avatar_mini");
                                nodeModel.avatar_large = nodeObj.getString("avatar_large");
                                nodeModel.avatar_normal = nodeObj.getString("avatar_normal");
                            }
                            model.nodeModel = nodeModel;

                            list.add(model);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                callback.onSuccResponse(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("getFastNews", "error" + error.toString());

                callback.onFailResponse();
            }
        });

        requestQueue.add(request);
    }

    public void getProfile(String username, final ProfileListener callback) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, profile_url + "?username=" + username, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("getPrifile succ ", response.toString());

                if (response != null) {

                    V2exProfileModel model = new V2exProfileModel();
                    try {
                        model.status = response.getString("status");
                        model.iid = response.getString("id");
                        model.username = response.getString("username");
                        model.website = response.getString("website");
                        model.twitter = response.getString("twitter");
                        model.psn = response.getString("psn");
                        model.github = response.getString("github");
                        model.btc = response.getString("btc");
                        model.location = response.getString("location");
                        model.tagline = response.getString("tagline");
                        model.bio = response.getString("bio");
                        model.avatar_mini = response.getString("avatar_mini");
                        model.avatar_large = response.getString("avatar_large");
                        model.avatar_normal = response.getString("avatar_normal");
                        model.created = response.getString("created");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callback.onSuccResponse(model);
                } else {

                    callback.onFailResponse();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("getProfile fail", error.toString());

                callback.onFailResponse();
            }
        });

        requestQueue.add(request);
    }

    public void getNodeNews(final NodeNewsListener callback) {

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, all_node_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("getNodeNews", response.toString());

                ArrayList<V2exNodeListModel> list = new ArrayList<V2exNodeListModel>();
                for (int i = 0; i < response.length(); i ++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (object != null) {

                            V2exNodeListModel model = new V2exNodeListModel();
                            model.iid = object.getString("id");
                            model.vname = object.getString("name");
                            model.title = object.getString("title");
                            model.title_alternative = object.getString("title_alternative");
                            model.topics = object.getString("topics");
                            model.header = object.getString("header");
                            model.footer = object.getString("footer");
                            model.created = object.getString("created");

                            if (object.isNull("header")) {
                                model.header = null;
                            }

                            if (object.isNull("footer")) {
                                model.footer = null;
                            }

                            list.add(model);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                callback.onSuccResponse(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("getNodeNews", error.toString());

                callback.onFailResponse();
            }
        });

        requestQueue.add(request);
    }

    public void getNodeTopics(String node_id, final NodeTopicsListener callback) {

        String url = node_topics_url + "?" + "node_id=" + node_id;

        Log.i("getNodeTopics", url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("getNodeTopics", response.toString());

                ArrayList<V2exTopicModel> list = new ArrayList<V2exTopicModel>();
                for (int i = 0; i < response.length(); i ++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if (obj != null) {
                            V2exTopicModel model = new V2exTopicModel();
                            model.iid = obj.getString("id");
                            model.title = obj.getString("title");
                            model.url = obj.getString("url");
                            model.content = obj.getString("content");
                            model.content_rendered = obj.getString("content_rendered");
                            model.replies = obj.getString("replies");
                            model.created = obj.getString("created");
                            model.last_modified = obj.getString("last_modified");
                            model.last_touched = obj.getString("last_touched");

                            V2exMemberModel memberModel = new V2exMemberModel();
                            JSONObject memberObj = obj.getJSONObject("member");
                            if (memberObj != null) {
                                memberModel.iid = memberObj.getString("id");
                                memberModel.username = memberObj.getString("username");
                                memberModel.tagline = memberObj.getString("tagline");
                                memberModel.avatar_mini = memberObj.getString("avatar_mini");
                                memberModel.avatar_normal = memberObj.getString("avatar_normal");
                                memberModel.avatar_large = memberObj.getString("avatar_large");
                            }
                            model.memberModel = memberModel;

                            V2exNodeModel nodeModel = new V2exNodeModel();
                            JSONObject nodeObj = obj.getJSONObject("node");
                            if (nodeObj != null) {
                                nodeModel.iid = nodeObj.getString("id");
                                nodeModel.vname = nodeObj.getString("name");
                                nodeModel.title = nodeObj.getString("title");
                                nodeModel.title_alternative = nodeObj.getString("title_alternative");
                                nodeModel.url = nodeObj.getString("url");
                                nodeModel.topics = nodeObj.getString("topics");
                                nodeModel.avatar_mini = nodeObj.getString("avatar_mini");
                                nodeModel.avatar_large = nodeObj.getString("avatar_large");
                                nodeModel.avatar_normal = nodeObj.getString("avatar_normal");
                            }
                            model.nodeModel = nodeModel;

                            list.add(model);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccResponse(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onFailResponse();
            }
        });

        requestQueue.add(request);
    }

    public void getTopicReplies(String topic_id, final TopicRepliesListener callback) {

        String url = replies_url + "?" + "topic_id=" + topic_id;

        Log.i("getTopicsReplies", url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                callback.onSuccResponse(null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.onFailResponse();
            }
        });

        requestQueue.add(request);
    }
}
