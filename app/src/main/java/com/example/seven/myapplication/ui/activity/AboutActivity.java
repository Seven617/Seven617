package com.example.seven.myapplication.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.view.TitleBar;

public class AboutActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private String title;
    private TextView tversion;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getview();
        titleBar();
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

    private void getview() {
        getVersion();
        tversion = (TextView) findViewById(R.id.version);
        titleBar = (TitleBar) findViewById(R.id.about_bar);
        tversion.setText(version);
    }


    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "关于我们";
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
            AboutActivity.this.finish();
        }
    };
}
