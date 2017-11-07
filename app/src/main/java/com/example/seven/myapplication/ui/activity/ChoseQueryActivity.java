package com.example.seven.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.adapter.ViewPagerIndicator;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.ui.fragment.FirstFragment;
import com.example.seven.myapplication.ui.fragment.SecondFragment;
import com.example.seven.myapplication.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class ChoseQueryActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private String title;
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mList;
    private List<String> mDatas;
    private int itemCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_query);
        //获取控件
        getview();
        //标题
        titleBar();
    }

    //获取控件
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.chosequery_bar);
        viewPager = (ViewPager) findViewById(R.id.vp);
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);

        mList = new ArrayList<>();
        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        mList.add(firstFragment);
        mList.add(secondFragment);

        mDatas = new ArrayList<>();
        mDatas.add("单条查询");
        mDatas.add("多条查询");


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };

        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(itemCount);

        //将viewpager与indicator绑定
        indicator.setDatas(mDatas);
        indicator.setViewPager(viewPager);
    }

    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "选择查询类型";
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
            ChoseQueryActivity.this.finish();
        }
    };
//    @Override
//    protected void onNetworkConnected(NetUtils.NetType type) {
//
//    }
//
//    @Override
//    protected void onNetworkDisConnected() {
//
//    }
}
