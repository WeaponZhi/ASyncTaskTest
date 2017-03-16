package com.weaponzhi.asynctasktest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ImageLoader 图片加载类
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/16 19:42 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class ImageLoader {
    private String tagUrl;
    private ImageView imageView;
    private LruCache<String, Bitmap> lruCache;

    public ImageLoader() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            lruCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        return lruCache.get(url);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (imageView.getTag().equals(tagUrl))
                imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    public void showImageByThread(ImageView imageView, final String url) {
        this.imageView = imageView;
        tagUrl = url;
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {
            new Thread(
            ) {
                @Override
                public void run() {
                    super.run();
                    Bitmap bitmap = getBitmapFromUrl(url);
                    if (bitmap != null) {
                        addBitmapToCache(url, bitmap);
                    }
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }
            }.start();
        } else {
            imageView.setImageBitmap(bitmap);
        }


    }

    public void showImageByAsyncTask(ImageView imageView, String url) {
        if (getBitmapFromCache(url) == null)
            new NewAsyncTask(imageView, url).execute(url);
        else
            imageView.setImageBitmap(getBitmapFromCache(url));
    }

    private class NewAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private String url;

        public NewAsyncTask(ImageView imageView, String url) {
            this.imageView = imageView;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = getBitmapFromUrl(params[0]);
            if (bitmap != null)
                addBitmapToCache(params[0], bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageView.getTag().equals(url))
                imageView.setImageBitmap(bitmap);
        }
    }


    public Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL Url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
