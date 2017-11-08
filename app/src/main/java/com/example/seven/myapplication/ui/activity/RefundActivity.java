package com.example.seven.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.ClearEditText;
import com.example.seven.myapplication.view.TitleBar;

import cn.bingoogolapple.qrcode.core.QRCodeView;

import static android.content.ContentValues.TAG;

//退款
public class RefundActivity extends BaseActivity implements QRCodeView.Delegate{
    private TitleBar titleBar;
    private String title;
    private String refundsSn;
    private Button btn_sure;
    private LinearLayout show_refundable;
    private LinearLayout gone_refundable;
    private QRCodeView mQRCodeView;
    private ClearEditText refunds_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refundable);
        //获取控件
        getView();
        //标题
        titleBar();
    }

    //网络连接状态（一切正常显示）
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        show_refundable.setVisibility(View.VISIBLE);
        gone_refundable.setVisibility(View.GONE);
    }

    //网络断开状态（原本界面隐藏 显示"当前网络不可用"）
    @Override
    protected void onNetworkDisConnected() {
        show_refundable.setVisibility(View.GONE);
        gone_refundable.setVisibility(View.VISIBLE);
    }

    //获取控件
    private void getView() {
        titleBar = (TitleBar) findViewById(R.id.refunds_bar);
        btn_sure = (Button) findViewById(R.id.refundable_btn_sure);
        refunds_edittext= (ClearEditText) findViewById(R.id.refunds_edittext);
        show_refundable = (LinearLayout) findViewById(R.id.show_refundable);
        gone_refundable = (LinearLayout) findViewById(R.id.gone_refundable);
        mQRCodeView = (QRCodeView) findViewById(R.id.refunds_zxingview);
        mQRCodeView.setDelegate(this);
    }

    //标题
    private void titleBar() {
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "退款界面";
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
            RefundActivity.this.finish();
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.changeToScanBarcodeStyle();
        mQRCodeView.startSpotDelay(100);//延迟100毫秒开始识别
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

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        showToast(result);
        refundsSn=result;
        refunds_edittext.setText(refundsSn);
        vibrate();
        mQRCodeView.startSpotDelay(5000);
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
