package com.weaponzhi.asynctasktest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * ProgressBarTest 模拟进度条
 * AsyncTask是根据线程池来进行的，必须等到前面的线程结束之后才会进行下个线程操作
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/16 12:03 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */


public class ProgressBarTest extends AppCompatActivity{
    private ProgressBar progressBar;
    private MyAsyncTask myAsyncTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        progressBar = (ProgressBar) findViewById(R.id.pg);
        myAsyncTask = new MyAsyncTask();//UI线程调用
        myAsyncTask.execute();//UI线程调用,只能调用一次，多次调用将产生运行时异常
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myAsyncTask!=null &&
                myAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            //cancel方法只是将对应的AsyncTask标记为cancel状态，并不是真正的取消线程执行
            //通过cancel标记信号量，通过判定语句尽快结束进程操作
            myAsyncTask.cancel(true);
        }
    }

    class MyAsyncTask extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //模拟进度更新
            for (int i = 0; i < 100; i++) {
                if (isCancelled()){
                    break;
                }
                publishProgress(i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (isCancelled())
                return;
            //获取进度更新值
            progressBar.setProgress(values[0]);
        }
    }
}
