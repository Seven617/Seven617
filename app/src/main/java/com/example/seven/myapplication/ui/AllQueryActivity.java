package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.service.QueryOrderService;
import com.example.seven.myapplication.view.SwipyRefreshLayout;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.adapter.MyAdapter;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class AllQueryActivity extends BaseActivity implements MyAdapter.OnRecyclerViewItemClickListener, SwipyRefreshLayout.OnRefreshListener {
    private ArrayList<QueryOrderData> arrayList;
    private RecyclerView recyclerView;
    private TitleBar titleBar;
    private String title;
    private MyAdapter adapter;
    private QueryOrderService queryOrderService;

    private SwipyRefreshLayout refreshLayout;

    private LinearLayoutManager linearLayoutManager;

    private  int pages = 1;

    private final int pagesSize = 5;

    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_query);
        //获取控件
        getView();
        //标题
        titleBar();

    }

    private void getView() {
        titleBar = (TitleBar) findViewById(R.id.allquery_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recyerview);
        refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        arrayList = new ArrayList();
        initData(pages);
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

        queryOrderService = new QueryOrderService();
        queryOrderService.queryOrder(pagesSize, pages, new CommonCallback<NetworkResult<List<QueryOrderData>>>() {


                    @Override
                    public void onSuccess(NetworkResult<List<QueryOrderData>> data) {
                        if(APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())){
                            if(null == data.getData() || data.getData().size()==0){
                                showToast("没有更多信息");
                            }else {
                                arrayList.addAll(data.getData());
                                adapter.notifyDataSetChanged();

                            }
                            refreshLayout.setRefreshing(false);
                        }else {
                            showToast(data.getMsg());
                        }

                    }

                    @Override
                    public void onFailure(String error_code, String error_message) {

                    }
                });


//                //使用retrofit配置api
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://gank.io/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//        APIConstants api = retrofit.create(APIConstants.class);
//        Call<QueryOrderData> call = api.getData(5, pages);
//        call.enqueue(new Callback<QueryOrderData>() {
//            @Override
//            public void onResponse(Call<QueryOrderData> call, Response<QueryOrderData> response) {
//
////                arrayList.addAll(response.body().getAmount());
//                adapter.notifyDataSetChanged();
//                Log.i("aaaa", arrayList.size() + "");
//                refreshLayout.setRefreshing(false);
//
//            }
//
//            @Override
//            public void onFailure(Call<QueryOrderData> call, Throwable t) {
//                refreshLayout.setRefreshing(false);
//            }
//        });
    }

    @Override
    public void onItemClick(View view, QueryOrderData data) {
        //显示订单具体内容
        showToast(data.toString());
    }

    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);
        showToast("已经是最新数据");
    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
        showToast("加载完成");
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                arrayList.clear();
                initData(pages);
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
