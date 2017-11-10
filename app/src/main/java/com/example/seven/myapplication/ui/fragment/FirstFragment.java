package com.example.seven.myapplication.ui.fragment;

import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.service.PrintPosService;
import com.example.seven.myapplication.service.QueryOrderService;
import com.example.seven.myapplication.ui.activity.RefundActivity;
import com.example.seven.myapplication.util.DateStyle;
import com.example.seven.myapplication.util.DateUtil;
import com.example.seven.myapplication.view.ClearEditText;

import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;

import static android.content.ContentValues.TAG;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by asus on 2016/3/26.
 */
public class FirstFragment extends BaseFragment implements QRCodeView.Delegate {
    private ClearEditText clearEditText;
    private String orderSn;
    private Button btn_sure;
    private LinearLayout show_query;
    private LinearLayout gone_query;
    private PopupWindow popupWindow;
    private QRCodeView mQRCodeView;
    private QueryOrderService queryOrderService;
    private PrintPosService printPosService ;
    private QueryOrderData queryOrderData ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_query;
    }

    @Override
    protected void initView() {
        btn_sure = findView(R.id.query_btn_sure);
        clearEditText = findView(R.id.query_edittext);
        show_query = findView(R.id.show_query);
        gone_query = findView(R.id.gone_query);
        mQRCodeView = findView(R.id.query_zxingview);
        mQRCodeView.setDelegate(this);
        btn_sure.setOnClickListener(OK);
    }

    @Override
    protected void initData() {
        //绑定设备开启
        bindDeviceService();
        //实现printer方法
        printPosService  = new PrintPosService(getActivity());

    }
    //btn按钮点击事件
    View.OnClickListener OK = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            queryOrderService =new QueryOrderService();
            orderSn = clearEditText.getText().toString();
            queryOrderService.queryByOrderNo(orderSn, new CommonCallback<NetworkResult<List<QueryOrderData>>>() {
                @Override
                public void onSuccess(NetworkResult<List<QueryOrderData>>  data) {
                    if(APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())){
                        if( null == data.getData() || data.getData().size() == 0){
                            showToast("未找到该订单");
                        }else {
//                        showToast(data.getData().get(0).getAmount());
                        showpopupWindow(v,data.getData().get(0));
                        }
                    }else {
                        checkoutTokenLost(data.getStatus(),getActivity());

                        showToast(data.getMsg());
                    }

                }

                @Override
                public void onFailure(String error_code, String error_message) {

                }
            });
        }
    };


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
    public void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.changeToScanBarcodeStyle();
        mQRCodeView.startSpotDelay(100);//延迟100毫秒开始识别
        mQRCodeView.showScanRect();
    }

    @Override
    public void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        showToast(result);
        orderSn=result;
        clearEditText.setText(orderSn);
        vibrate();
        mQRCodeView.startSpotDelay(5000);
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




}
