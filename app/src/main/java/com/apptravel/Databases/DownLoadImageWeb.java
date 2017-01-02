package com.apptravel.Databases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.apptravel.Entity.Travel;
import com.apptravel.Events.ImageLoadedEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lldti on 01-Jan-17.
 */

public class DownLoadImageWeb extends AsyncTask<String, String, Drawable> {

    private static final String TAG = DownLoadImageWeb.class.getSimpleName();
    private ImageLoadedEvent imageLoadedEvent;

    public DownLoadImageWeb(ImageLoadedEvent imageLoadedEvent) {
        this.imageLoadedEvent = imageLoadedEvent;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Drawable doInBackground(String... params) {
        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(params[0]);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            Log.d(TAG, "expetion cant convert URL");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "expetion cant open connection");
            e.printStackTrace();
        }
        return new BitmapDrawable(bmp);
    }

    @Override
    protected void onPostExecute(Drawable drawable) {

        imageLoadedEvent.onImageLoaded(drawable);
    }
}
