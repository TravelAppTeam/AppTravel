package com.apptravel.Databases;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.apptravel.Entity.Travel;
import com.apptravel.Events.AsyncResponse;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class NewDatabase {
    private static final String TAG = NewDatabase.class.getSimpleName();
    private AsyncResponse response = null;
    private CircularProgressView circularProgressView;
    public NewDatabase(CircularProgressView circularProgressView) {
        this.circularProgressView = circularProgressView;
    }

    public void setAsyncResponse(AsyncResponse response) {
        this.response = response;
    }

    void execute(String url) {
        new readJson().execute(url);
    }

    private String readContentFromUrl(String str) {
        URL url = null;
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "in read content from url method");
        String input = "";
        try {
            if (url == null) {
                Log.e(TAG, "Url is null");
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            Log.i(TAG, "read content");
            String ip;
            while ((ip = in.readLine()) != null) {
                input += ip;
            }

        } catch (IOException e) {
            Log.e(TAG, "cant read url");
        }
        return input;
    }

    private class readJson extends AsyncTask<String, Integer, ArrayList<Travel>> {

        @Override
        protected void onPreExecute() {
            ObjectAnimator animator = ObjectAnimator.ofInt(circularProgressView, "progress", 0, 200);
            animator.setInterpolator(new DecelerateInterpolator());
            if(circularProgressView != null) {
                circularProgressView.setVisibility(View.VISIBLE);
                circularProgressView.startAnimation();
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Travel> doInBackground(String... params) {
            String content = readContentFromUrl(params[0]);
            return ReadJson.readStringJson(content);
        }

        @Override
        protected void onPostExecute(ArrayList<Travel> listTravel) {
            if (listTravel == null) {
                Log.e(TAG, "List Travel is null");
                listTravel = new ArrayList<>();
            }
            if(circularProgressView != null) {
                circularProgressView.stopAnimation();
                circularProgressView.setVisibility(View.GONE);
            }
            if(response != null)
                response.onAsyncLoadDone(listTravel);
            else
                Log.d(TAG, "response is null");
        }
    }
}
