package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.AmountEditText;
import com.example.seven.myapplication.view.TitleBar;

public class WeChatActivity extends BsaeActivity {
    private TitleBar titleBar;
    private String title;
    private AmountEditText amountEditText;
    private Button btn_sure;
    private LinearLayout show_wechat;
    private LinearLayout gone_wechat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);
        //获取控件
        getview();
        //标题
        titleBar();
    }

    //网络连接状态（一切正常显示）
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        show_wechat.setVisibility(View.VISIBLE);
        gone_wechat.setVisibility(View.GONE);
    }

    //网络断开状态（原本界面隐藏 显示"当前网络不可用"）
    @Override
    protected void onNetworkDisConnected() {
        show_wechat.setVisibility(View.GONE);
        gone_wechat.setVisibility(View.VISIBLE);
    }

    //获取控件
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.wechatpay_bar);
        btn_sure = (Button) findViewById(R.id.wechat_btn_sure);
        show_wechat = (LinearLayout) findViewById(R.id.show_wechat);
        gone_wechat = (LinearLayout) findViewById(R.id.gone_wechat);
        amountEditText = (AmountEditText) findViewById(R.id.wechat_edit_amount);
        //设置输入框数字的倍数
        //amountEditText.setMultiple(100);
        btn_sure.setOnClickListener(OK);
    }

    //btn按钮点击事件
    View.OnClickListener OK = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (amountEditText.isConformRules()) {
                ShowToast(amountEditText.getContent());
            } else {
                ShowToast("输入内容不符合规则！！！");
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
        title = "微信二维码/条码";
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
            WeChatActivity.this.finish();
        }
    };
}
