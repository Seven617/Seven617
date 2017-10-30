package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;

//退款
public class RefundableActivity extends BsaeActivity {
    private TitleBar titleBar;
    private String title;
    private Button btn_sure;
    private LinearLayout show_refundable;
    private LinearLayout gone_refundable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refundable);
        //获取控件
        getview();
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
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.refunds_bar);
        btn_sure = (Button) findViewById(R.id.refundable_btn_sure);
        show_refundable = (LinearLayout) findViewById(R.id.show_refundable);
        gone_refundable = (LinearLayout) findViewById(R.id.gone_refundable);
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
            RefundableActivity.this.finish();
        }
    };


}
