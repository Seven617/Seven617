package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.device.PrinterImpl;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.PayService;
import com.example.seven.myapplication.view.TitleBar;
import com.landicorp.android.eptapi.device.Printer;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class PayScannerActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final String TAG = PayScannerActivity.class.getSimpleName();
    private QRCodeView mQRCodeView;
    private TitleBar titleBar;
    private String title;
    private TextView zfbPayText;
    private String zfbpayAmount;//支付宝支付金额
    private PayService payService;
    private PrinterImpl printer ;
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
        zfbPayText = (TextView) findViewById(R.id.zfb_pay_text);
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        zfbpayAmount =intent.getStringExtra("amount");
        mQRCodeView.setDelegate(this);
        zfbPayText.setText(zfbpayAmount +"元");
    }

    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "进行扫码";
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
            PayScannerActivity.this.finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
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
        payService.pay(zfbpayAmount, result, new CommonCallback<NetworkResult<String>>() {
            @Override
            public void onSuccess(NetworkResult<String> data) {

                if(APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())){
                    start("快变富信息技术有限公司",zfbpayAmount,data.getData());

                    //扫码成功跳转到下一个页面
                    Intent  intent=new Intent(PayScannerActivity.this, PaySuccessActivity.class);
                    intent.putExtra("amount", zfbpayAmount);
                    startActivity(intent);
                    PayScannerActivity.this.finish();

                }else {
                    //失败则跳出失败原因
                    showToast(data.getMsg());
                }

            }

            @Override
            public void onFailure(String error_code, String error_message) {
                //网络连接故障时的响应
                showToast("网络故障请检查网络");
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


    @Override
    protected void onResume() {
        super.onResume();
        bindDeviceService();

        printer= new PrinterImpl(this) {
            @Override
            protected void onDeviceServiceCrash() {

            }

            @Override
            protected void displayInfo(String info) {

            }
        };
    }

    public void start(String comName,String amount,String orderNo ) {
        int ret = printer.getPrinterStatus();
        if (ret != Printer.ERROR_NONE) {
            showToast(printer.getDescribe(ret));
            return;
        }
        printer.init();
        if(!printer.addBitmap()) {
            showToast("add bitmap fail");
            return;
        }
        if (!printer.addText(comName,amount)) {
            showToast("add text fail");
            return;
        }
        if (!printer.addBarcode(orderNo)) {
            showToast("add barcode fail");
            return;
        }

        if (!printer.addText(orderNo)) {
            showToast("add text fail");
            return;
        }
//        if (!printer.addQRcode()) {
//            showToast("add qrcode fail");
//            return;
//        }
        if (!printer.feedLine(3)) {
            showToast("feed line fail");
            return;
        }
        if (!printer.cutPage()) {
            showToast("cut page fail");
            return;
        }
        printer.startPrint();
    }
}
