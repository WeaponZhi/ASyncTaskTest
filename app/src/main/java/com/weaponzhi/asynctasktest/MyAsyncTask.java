package com.weaponzhi.asynctasktest;

import android.os.AsyncTask;
import android.util.Log;

/**
 * MyAsyncTask 自定义AsyncTask
 * 调用顺序 onPreExecute->doInBackground->onProgressUpdate->onPostExecute
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/15 18:12 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */


public class MyAsyncTask extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... params) {
        Log.d("xys","doInBackgroud");
        publishProgress();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("xys","onPreExecute");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("xys","onPostExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d("xys","onProgressUpdate");
    }
}
