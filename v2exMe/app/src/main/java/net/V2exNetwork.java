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
import org.json.JSONObject;

import java.util.ArrayList;


public class V2exNetwork {

    public static String hotnews_url = "https://www.v2ex.com/api/topics/hot.json";
    public static String fastnews_url = "https://www.v2ex.com/api/topics/latest.json";

    public interface HotNewsListener {
        public void onSuccResponse(ArrayList<?> responseList);
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

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, hotnews_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("getHotNews ", response.toString());
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
