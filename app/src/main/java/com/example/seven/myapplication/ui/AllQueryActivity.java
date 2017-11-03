package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.view.SwipyRefreshLayout;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.adapter.MyAdapter;
import com.example.seven.myapplication.model.QueryResult;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllQueryActivity extends BaseActivity implements MyAdapter.OnRecyclerViewItemClickListener, SwipyRefreshLayout.OnRefreshListener {
    private ArrayList<QueryResult.Info> arrayList;
    private RecyclerView recyclerView;
    private TitleBar titleBar;
    private String title;
    private MyAdapter adapter;

    private SwipyRefreshLayout refreshLayout;

    private LinearLayoutManager linearLayoutManager;

    private int pages = 1;

    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_query);
        //获取控件
        getview();
        //标题
        titleBar();

    }

    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.allquery_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recyerview);
        refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        arrayList = new ArrayList();
        initData(1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(this, arrayList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "所有单子查询";
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
            AllQueryActivity.this.finish();
        }
    };
    private void initData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIConstants api = retrofit.create(APIConstants.class);
        Call<QueryResult> call = api.getData(5, pages);
        call.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {

                arrayList.addAll(response.body().results);
                adapter.notifyDataSetChanged();
                Log.i("aaaa", arrayList.size() + "");
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(View view, QueryResult.Info data) {
        ShowToast("click item " + data);
    }

    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);
        ShowToast("已经是最新数据");
    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
        ShowToast("加载完成");
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                arrayList.clear();
                initData(1);
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                pages++;
                initData(pages);
                break;
        }
        // adapter.notifyDataSetChanged();

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
