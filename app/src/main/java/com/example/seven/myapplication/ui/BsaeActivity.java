package com.example.seven.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.example.seven.myapplication.network.NetChangeObserver;
import com.example.seven.myapplication.network.NetStateReceiver;
import com.example.seven.myapplication.network.NetUtils;

public abstract class BsaeActivity extends Activity {
    /**
     * 网络观察者
     */
    protected NetChangeObserver mNetChangeObserver = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 网络改变的一个回掉类
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                onNetworkDisConnected();
            }
        };

        //开启广播去监听 网络 改变事件
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 网络连接状态
     *
     * @param type 网络状态
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * 网络断开的时候调用
     */
    protected abstract void onNetworkDisConnected();
    //Toast及时反应
    private Toast mToast;

    public void ShowToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
