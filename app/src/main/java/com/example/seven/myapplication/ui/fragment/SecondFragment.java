package com.example.seven.myapplication.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.adapter.MyAdapter;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.service.PrintPosService;
import com.example.seven.myapplication.service.QueryOrderService;
import com.example.seven.myapplication.util.DateStyle;
import com.example.seven.myapplication.util.DateUtil;
import com.example.seven.myapplication.view.SwipyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/3/26.
 */
public class SecondFragment extends BaseFragment implements MyAdapter.OnRecyclerViewItemClickListener, SwipyRefreshLayout.OnRefreshListener {
    private ArrayList<QueryOrderData> arrayList;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private QueryOrderService queryOrderService;
    private SwipyRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private PopupWindow popupWindow;
    private PrintPosService printPosService ;
    private int pages = 1;
    private QueryOrderData queryOrderData;
    private final int pagesSize = 8;
    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_query;
    }

    @Override
    protected void initView() {
        recyclerView = findView(R.id.recyerview);
        refreshLayout = findView(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        arrayList = new ArrayList();
        initData(pages);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(getContext(), arrayList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        //绑定设备开启
        bindDeviceService();
        //实现printer方法
        printPosService  = new PrintPosService(getActivity());
    }

    private void initData(int pages) {

        queryOrderService = new QueryOrderService();
        queryOrderService.queryOrder(pagesSize, pages, new CommonCallback<NetworkResult<List<QueryOrderData>>>() {


            @Override
            public void onSuccess(NetworkResult<List<QueryOrderData>> data) {
                if (APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())) {
                    if (null == data.getData() || data.getData().size() == 0) {
                        showToast("没有更多信息");
                    } else {
                        arrayList.addAll(data.getData());
                        adapter.notifyDataSetChanged();
                    }
                    refreshLayout.setRefreshing(false);
                } else {
                    showToast(data.getMsg());
                }

            }

            @Override
            public void onFailure(String error_code, String error_message) {

            }
        });
    }

    @Override
    public void onItemClick(View view, QueryOrderData data) {

        showpopupWindow(view,data);// 显示PopupWindow
    }

    private void showpopupWindow(View v,QueryOrderData data) {
        queryOrderData = data;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.querypopupwindow, null);
        popupWindow = new PopupWindow(view, 500, 700, true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        //这里获取悬浮框的控件
        TextView orderSnTextView = (TextView)view.findViewById(R.id.order_sn_text);
//        TextView tradeNoTextView = (TextView)view.findViewById(R.id.trade_no_text);
        TextView amountTextView = (TextView)view.findViewById(R.id.pop_amount);
        TextView payTimeTextView = (TextView)view.findViewById(R.id.pay_time_text);
        TextView payTypeTextView = (TextView)view.findViewById(R.id.pay_type_text);
        TextView payStatusTextView = (TextView)view.findViewById(R.id.pay_status_text);
        orderSnTextView.setText(data.getOrderSn());
//        tradeNoTextView.setText(data.getTradeNo());
        amountTextView.setText(data.getAmount());
        payTimeTextView.setText(DateUtil.TimestampToString(Long.valueOf(data.getModifyDate()), DateStyle.YYYY_MM_DD_HH_MM_SS_EN));
        payTypeTextView.setText(data.getPayTypeTxt());
        payStatusTextView.setText(data.getStatus());
        //下面的那些控件你自己获取一下
        Button querypopbtnsure = (Button) view.findViewById(R.id.query_popsure);//左边确定按钮
        Button querypopprint = (Button) view.findViewById(R.id.query_popprint);//右边打印按钮
        querypopbtnsure.setOnClickListener(doit);
        querypopprint.setOnClickListener(doit);
        // PopupWindow弹出位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        backgroundAlpha(0.7f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    View.OnClickListener doit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.query_popsure){
                popupWindow.dismiss();
            }
            else{
                printPosService.printQueryOrder(queryOrderData);

//                showToast("打印");
            }
        }
    };

    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getActivity().getWindow().setAttributes(lp);
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

}
