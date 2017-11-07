package com.example.seven.myapplication.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.AmountEditText;
import com.example.seven.myapplication.view.TitleBar;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AmountActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private TitleBar titleBar;
    private String title;
    private AmountEditText amountEditText;
    private Button btn_sure;
    private LinearLayout show_zfb;
    private LinearLayout gone_zfb;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb);
        //获取控件
        getview();
        //标题
        titleBar();
    }

    //网络连接状态（一切正常显示）
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        show_zfb.setVisibility(View.VISIBLE);
        gone_zfb.setVisibility(View.GONE);
    }

    //网络断开状态（原本界面隐藏 显示"当前网络不可用"）
    @Override
    protected void onNetworkDisConnected() {
        show_zfb.setVisibility(View.GONE);
        gone_zfb.setVisibility(View.VISIBLE);
    }

    //获取控件
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.zfbpay_bar);
        btn_sure = (Button) findViewById(R.id.zfb_btn_sure);
        show_zfb = (LinearLayout) findViewById(R.id.show_zfb);
        gone_zfb = (LinearLayout) findViewById(R.id.gone_zfb);
        amountEditText = (AmountEditText) findViewById(R.id.zfb_edit_amount);
        btn_sure.setOnClickListener(OK);
    }

    //btn按钮点击事件
    View.OnClickListener OK = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (amountEditText.isConformRules()) {
//                showToast(amountEditText.getContent());
                Intent  intent=new Intent(AmountActivity.this, PayScannerActivity.class);
                intent.putExtra("amount", amountEditText.getContent());
                startActivity(intent);
                AmountActivity.this.finish();
            } else {
                showToast("输入内容不符合规则！！！");
            }
        }
    };

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
            AmountActivity.this.finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
