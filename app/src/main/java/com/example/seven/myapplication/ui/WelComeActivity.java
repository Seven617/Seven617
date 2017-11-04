package com.example.seven.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Seven on 2017-10-26
 */

public class WelComeActivity extends BaseActivity {
    private static final int FAILURE = 0; // 无网络
    private static final int SUCCESS = 1; // 有网络
    private static final int OFFLINE = 2; // 如果支持离线阅读，进入离线模式
    private static final int SHOW_TIME_MIN = 800;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            WelComeActivity.this.finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_welcome);
        new MyAsyncTask().execute();
    }
    //网络连接状态
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }
    //网络断开状态
    @Override
    protected void onNetworkDisConnected() {

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
            //showToast("54617");
                Intent intent = new Intent(WelComeActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                WelComeActivity.this.finish();
            } else {
                showToast("请检测网络连接");
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

