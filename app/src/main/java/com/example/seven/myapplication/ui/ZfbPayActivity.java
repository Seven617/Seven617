package com.example.seven.myapplication.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.PayResult;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.PayService;
import com.example.seven.myapplication.view.TitleBar;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZfbPayActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final String TAG = ZfbPayActivity.class.getSimpleName();
    private QRCodeView mQRCodeView;
    private TitleBar titleBar;
    private String title;
    private TextView zfb_pay_text;
    private String zfbpay_amount;//支付宝支付金额
    private PayService payService;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb_pay);
        getview();
        //标题
        titleBar();
    }

    public void getview() {
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        titleBar = (TitleBar) findViewById(R.id.Ttoolbar);
        zfb_pay_text = (TextView) findViewById(R.id.zfb_pay_text);
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        zfbpay_amount=intent.getStringExtra("data");
        mQRCodeView.setDelegate(this);
        zfb_pay_text.setText(zfbpay_amount+"元");
    }

    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "支付宝二维码/条码";
        titleBar.setTitle(title);
        titleBar.setTitleSize(20);
        titleBar.setTitleColor(Color.WHITE);
        //下滑分割线
        titleBar.setDividerColor(Color.GRAY);
        //设置titleBar背景颜色
        titleBar.setBackgroundResource(R.color.colorPrimaryDark);
        //左边返回按钮点击事件
        titleBar.setLeftClickListener(ck);
    }

    //左边返回按钮点击事件
    View.OnClickListener ck = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ZfbPayActivity.this.finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.startSpot();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        payService=new PayService();
        payService.pay(zfbpay_amount, result, new CommonCallback<String>() {
            @Override
            public void onSuccess(String data) {
                PayResult payResult = payService.getPayResult(data);
                if(payResult.isSuccess()){
                    ShowToast("显示结果："+payResult.getResult());
                }

            }

            @Override
            public void onFailure(String error_code, String error_message) {

            }
        });
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_barcode:
                mQRCodeView.changeToScanBarcodeStyle();
                break;
            case R.id.scan_qrcode:
                mQRCodeView.changeToScanQRCodeStyle();
                break;
        }
    }
}
