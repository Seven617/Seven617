package com.example.seven.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.seven.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BsaeActivity {
    private static final int FAILURE = 0; // 无网络
    private static final int SUCCESS = 1; // 有网络
    private static final int OFFLINE = 2; // 如果支持离线阅读，进入离线模式
    private static final int SHOW_TIME_MIN = 800;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            MainActivity.this.finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyAsyncTask().execute();
    }

    //检测网络
    class MyAsyncTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... arg0) {
            int result;
            //请求数据
            result = loadingCache();
            long startTime = System.currentTimeMillis();
            long loadingTime = System.currentTimeMillis() - startTime;
            if (loadingTime < SHOW_TIME_MIN) {
                try {
                    Thread.sleep(SHOW_TIME_MIN - loadingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        //执行操作
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == SUCCESS) {
//                ShowToast("54617");
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                MainActivity.this.finish();
            } else {
                ShowToast("请检测网络连接");
                timer.schedule(task, 2000);
            }
        }

        //判断网络
        public int loadingCache() {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null) {
                return FAILURE;
            } else {
                return SUCCESS;
            }
        }
    }
}

