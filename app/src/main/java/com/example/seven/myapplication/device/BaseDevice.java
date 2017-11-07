package com.example.seven.myapplication.device;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public abstract class BaseDevice {
	protected Context context;
    protected Handler uiHandler = new Handler(Looper.getMainLooper());
	public BaseDevice(Context context) {
		this.context = context;
	}

	@SuppressLint("ShowToast")
	protected void showNormalMessage(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	@SuppressLint("ShowToast")
	protected void showErrorMessage(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public Context getContext() {
		return context;
	}
	
	protected abstract void onDeviceServiceCrash();

	protected abstract void displayInfo(String info);
}
