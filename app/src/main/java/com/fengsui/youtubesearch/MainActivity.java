package com.fengsui.youtubesearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;


/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {
    public final static String TAG = "MainActivity";
    private RequestQueue queue;
    private MainFragment mMainFragment;
    //private LoginFragment mLoginFragment;
    public static LocalStorage db;
    private Context mContext;
    private SharedPreferences pref;
    private Tools dev;
    private String token;
    private LoadingFragment loadingFragment;
    private static final String url = "https://fservice.fengsui.com.tw";
    //private static final String url = "http://192.168.11.137";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dev = new Tools();
        mContext = this;
        db = new LocalStorage(mContext);
        mMainFragment = new MainFragment();
        pref = getSharedPreferences("iptv", MODE_PRIVATE);
        initQueue();
        token = pref.getString("token","empty");
        if (savedInstanceState == null) {
            showMain();
            sync();
        }
    }

    public void showMain() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_browse_fragment, mMainFragment)
                .commitNow();
    }

    public void showLoading() {
        loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_browse_fragment, loadingFragment,"loading").commit();
    }

    public void hideLoading() {
        if(loadingFragment!=null){
            getSupportFragmentManager().beginTransaction().remove(loadingFragment).commitNow();
            loadingFragment = null;
        }
    }

    private void initQueue() {
        Cache cache = new DiskBasedCache(getCacheDir(), 2 * 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();
    }

    public class FsiRequest extends StringRequest {
        public FsiRequest(int method,String url, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
            setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void sync() {
        showLoading();
        // Modify id to swap version between default = 1, fullon = 6 and midori = 9
        String uri = String.format("%s/api/list/%s?ip=%s&sdk=%d&dev=%s&version=%s&token=%s&id=1",
                url,dev.mac, dev.ip, Util.SDK_INT, Build.MODEL, Build.FINGERPRINT, token);
        Log.i(TAG, uri);
        queue.add(new FsiRequest(Request.Method.GET,uri ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG,response);
                        if(response.equals("ok")){
                            Log.i(TAG,"ok");
                            hideLoading();
                            return;
                        }
                        try {
                            JSONObject object = new JSONObject(response);
                            pref.edit().putString("token", object.getString("token")).commit();
                            JSONArray list = object.getJSONArray("list");
                            Log.d("data", list.toString());
                            db.truncate();
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject item = list.getJSONObject(i);
                                String name = item.getString("name");
                                JSONArray videos = item.getJSONArray("videos");
                                for(int j=0;j < videos.length(); j++){
                                    JSONObject v = videos.getJSONObject(j);
                                    db.addMovie(name,
                                            v.getString("title"),
                                            v.getString("channel"),
                                            v.getString("id"),
                                            v.getString("pic"));
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mMainFragment.loadRows();
                                }
                            });

                        }catch (Exception e){
                            Log.e(TAG, e.getMessage());
                        }
                        hideLoading();
                        //Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Log.e(TAG, "Sync Data Timeout");
                    sync();
                }
            }
        }));

    }

    public void login(String token) {
        showMain();
        pref.edit().putString("token", token).commit();
        sync();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}