package com.example.seven.myapplication.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.device.PrinterImpl;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.model.ScannerPayData;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.PayService;
import com.example.seven.myapplication.util.ToastUtils;
import com.example.seven.myapplication.view.ScanBoxView;
import com.example.seven.myapplication.view.TitleBar;
import com.example.seven.myapplication.zxing.camera.CameraManager;
import com.example.seven.myapplication.zxing.decode.DecodeThread;
import com.example.seven.myapplication.zxing.utils.BeepManager;
import com.example.seven.myapplication.zxing.utils.CaptureActivityHandler;
import com.example.seven.myapplication.zxing.utils.InactivityTimer;
import com.google.zxing.Result;
import com.landicorp.android.eptapi.device.Printer;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

import cn.bingoogolapple.qrcode.core.QRCodeView;
/**
 * Created by Seven on 2017/10/20.
 */
public final class PayScannerActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = PayScannerActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION_CAMERA = 0x2;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private ScanBoxView scanBoxView = null;
    private boolean isHasSurface = false;
    private Rect mCropRect = null;

    private TitleBar titleBar;
    private String title;
    private TextView zfbPayText;
    private String zfbpayAmount;//支付宝支付金额
    private PayService payService;
    private PrinterImpl printer;
    private CatLoadingView mView;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_zfb_pay);
        getview();
        //标题
        titleBar();
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanBoxView = (ScanBoxView) findViewById(R.id.capture_crop_view_v);


        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
    }

    public void getview() {
        titleBar = (TitleBar) findViewById(R.id.Ttoolbar);
        zfbPayText = (TextView) findViewById(R.id.zfb_pay_text);
        mView = new CatLoadingView();
        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        zfbpayAmount = intent.getStringExtra("amount");
        zfbPayText.setText(zfbpayAmount + "元");
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
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            initCamera(scanPreview.getHolder());
        } else {
            scanPreview.getHolder().addCallback(this);
        }
        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void handleDecode(Result rawResult) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        handleQCode(rawResult.getText());
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
            return;
        }

        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }
            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Camera error");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().height;
        int cameraHeight = cameraManager.getCameraResolution().width;
        mCropRect = scanBoxView.getScanBoxAreaRect(cameraWidth, cameraHeight);
    }

    private void handleQCode(String qCode) {
        if (TextUtils.isEmpty(qCode)) {
            ToastUtils.showToast(this, R.string.qcode_empty);
            return;
        } else {
            Log.i(TAG, "result:" + qCode);
            showDialog();
            detil(qCode);
        }
//        Toast.makeText(getApplicationContext(),qCode,Toast.LENGTH_LONG).show();
//        startWebActivity(qCode);
    }

    private void detil(String str) {
        payService = new PayService();
        payService.pay(zfbpayAmount, str, new CommonCallback<NetworkResult<ScannerPayData>>() {
            @Override
            public void onSuccess(NetworkResult<ScannerPayData> data) {
                mView.dismiss();
                if (APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())) {

                    //扫码成功跳转到下一个页面,将返回参数传到下一个页面
                    Intent intent = new Intent(PayScannerActivity.this, PaySuccessActivity.class);
                    intent.putExtra("data", JSON.toJSONString(data.getData()));
                    intent.putExtra(APIConstants.STRING_AMOUNT, zfbpayAmount);
                    startActivity(intent);
                    PayScannerActivity.this.finish();
                } else {
                    checkoutTokenLost(data.getStatus(), PayScannerActivity.this);

                    //失败则跳出失败原因
                    showToast(data.getMsg());
                }

            }

            @Override
            public void onFailure(String error_code, String error_message) {
                //网络连接故障时的响应
                mView.dismiss();
                showToast("网络故障请检查网络");
            }
        });
    }

    public void showDialog() {
        mView.show(getSupportFragmentManager(), "");
    }

    private void startWebActivity(String url) {
//        Intent intent = new Intent(this, WebActivity.class);
//        intent.putExtra(WebActivity.URL, url);
//        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initCamera(scanPreview.getHolder());
            } else {
                // Permission Denied
                ToastUtils.showToast(this, "Permission Denied");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
