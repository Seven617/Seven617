package com.example.seven.myapplication.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.network.NetChangeObserver;
import com.example.seven.myapplication.network.NetStateReceiver;
import com.example.seven.myapplication.network.NetUtils;
import com.jady.retrofitclient.HttpManager;
import com.jady.retrofitclient.RetrofitClient;
import com.landicorp.android.eptapi.DeviceService;
import com.landicorp.android.eptapi.exception.ReloginException;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.exception.ServiceOccupiedException;
import com.landicorp.android.eptapi.exception.UnsupportMultiProcess;
/**
 * Created by Seven on 2017/10/20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private boolean isDeviceServiceLogined;
    /**
     * 网络观察者
     */
    protected NetChangeObserver mNetChangeObserver = null;
    //在基类中初始化Dialog

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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


    public void showToast(String text) {
        try {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public void bindDeviceService() {
        try {
            isDeviceServiceLogined = false;
            DeviceService.login(this);
            isDeviceServiceLogined = true;
        } catch (RequestException e) {
            // Rebind after a few milliseconds,
            // If you want this application keep the right of the device service
//			runOnUiThreadDelayed(new Runnable() {
//				@Override
//				public void run() {
//					bindDeviceService();
//				}
//			}, 300);
            e.printStackTrace();
        } catch (ServiceOccupiedException e) {
            e.printStackTrace();
        } catch (ReloginException e) {
            e.printStackTrace();
        } catch (UnsupportMultiProcess e) {
            e.printStackTrace();
        }
    }

    /**
     * Release the right of using the device.
     */
    public void unbindDeviceService() {
        DeviceService.logout();
        isDeviceServiceLogined = false;
    }

    public void checkoutTokenLost(String status, Activity activity){
        if(APIConstants.CODE_RESULT_NO_LOGIN_ERROR.equals(status) || APIConstants.CODE_RESULT_SESSION_TIMEOUT_ERROR.equals(status)){
            Intent intent  =  new Intent(activity,LoginActivity.class);
            startActivity(intent);
            activity.finish();
        }

    }
}