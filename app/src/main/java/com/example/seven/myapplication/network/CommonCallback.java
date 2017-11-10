package com.example.seven.myapplication.network;

import android.widget.Toast;

import com.example.seven.myapplication.model.NetworkResult;
import com.jady.retrofitclient.HttpManager;
import com.jady.retrofitclient.callback.HttpCallback;

/**
 * Created by seven617 on 2017/11/1.
 */

public abstract class CommonCallback<T> extends HttpCallback<T> {
    @Override
    public void onResolve(T t) {
        NetworkResult networkResult= (NetworkResult)t;

        onSuccess(t);
    }

    @Override
    public void onFailed(String err_code, String message) {
        if (enableShowToast()) {
            Toast.makeText(HttpManager.mContext, message, Toast.LENGTH_SHORT);
        }
        onFailure(err_code, message);
    }


    public abstract void onSuccess(T data);


    public abstract void onFailure(String error_code, String error_message);

    public boolean enableShowToast() {
        return false;
    }
}
