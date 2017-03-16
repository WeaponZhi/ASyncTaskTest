package com.weaponzhi.asynctasktest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.weaponzhi.asynctasktest.adapter.BaseListView;
import com.weaponzhi.asynctasktest.bean.ImageBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewImageAsyncTaskActivity 异步加载图片进RecyclerView
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/16 17:42 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class RecyclerViewImageAsyncTaskActivity extends AppCompatActivity {
    private ListView lv_image;
    private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_image_asynctask);
        lv_image = (ListView) findViewById(R.id.lv_image);

        new NewsAsyncTask().execute(URL);
    }
    class NewsAsyncTask extends AsyncTask<String ,Void ,List<ImageBean>> {

        @Override
        protected List<ImageBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<ImageBean> imageBeen) {
            BaseListView adapter = new BaseListView(RecyclerViewImageAsyncTaskActivity.this,imageBeen);
            lv_image.setAdapter(adapter);
        }
    }

    private List<ImageBean> getJsonData(String url) {
        List<ImageBean> list = new ArrayList<>();
        try {
            String jsonString = readString(new URL(url).openStream());
            JSONObject jsonObject;
            ImageBean imageBean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    imageBean = new ImageBean();
                    imageBean.newsIconUrl = jsonObject.getString("picSmall");
                    imageBean.newsTitle = jsonObject.getString("name");
                    imageBean.newsContent= jsonObject.getString("description");
                    list.add(imageBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("xiaozhi",jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("xiaozhi", String.valueOf(list.size()));
        return list;
    }
    private String readString(InputStream is){
        InputStreamReader isr;
        String result = "";
        try {
            String line;
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine())!=null){
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
