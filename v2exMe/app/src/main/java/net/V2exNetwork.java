package net;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;

import Models.*;


public class V2exNetwork {

    public static String hotnews_url = "https://www.v2ex.com/api/topics/hot.json";
    public static String fastnews_url = "https://www.v2ex.com/api/topics/latest.json";

    public interface HotNewsListener {
        public void onSuccResponse(ArrayList<V2exHotNewsModel> responseList);
        public void onFailResponse();
    }

    public interface FastNewsListener {
        public void onSuccResponse(ArrayList<?> responseList);
        public void onFailResponse();
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

                           V2exMemberModel memberModel = new V2exMemberModel();
                           JSONObject memberObj = obj.getJSONObject("member");
                           if (memberObj != null) {
                               memberModel.iid = memberObj.getString("iid");
                               memberModel.username = memberObj.getString("username");
                               memberModel.tagline = memberObj.getString("tagline");
                               memberModel.avatar_mini = memberObj.getString("avatar_mini");
                               memberModel.avatar_normal = memberObj.getString("avatar_normal");
                               memberModel.avatar_large = memberObj.getString("avatar_large");
                           }
                           model.memberModel = memberModel;

                           V2exNodeModel nodeModel = new V2exNodeModel();
                           JSONObject nodeObj = obj.getJSONObject("node");
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("getHotNews", "error " + error.toString());
            }
        });

        requestQueue.add(request);
    }

    public void getFastNews(final FastNewsListener callback) {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, fastnews_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("getFastNews", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("getFastNews", "error" + error.toString());
            }
        });

        requestQueue.add(request);
    }
}
