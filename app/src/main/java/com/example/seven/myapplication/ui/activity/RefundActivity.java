package com.example.seven.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.PrintPosService;
import com.example.seven.myapplication.service.RefundService;
import com.example.seven.myapplication.util.DateStyle;
import com.example.seven.myapplication.util.DateUtil;
import com.example.seven.myapplication.view.ClearEditText;
import com.example.seven.myapplication.view.TitleBar;
import com.roger.catloadinglibrary.CatLoadingView;

import cn.bingoogolapple.qrcode.core.QRCodeView;

import static android.content.ContentValues.TAG;

//退款
public class RefundActivity extends BaseActivity implements QRCodeView.Delegate {

    private TitleBar titleBar;
    private String title;
    private String refundsSn;
    private Button btn_sure;
    private LinearLayout show_refundable;
    private LinearLayout gone_refundable;
    private QRCodeView mQRCodeView;
    private ClearEditText refunds_edittext;
    private PopupWindow popupWindow;
    private RefundService refundService;
    private EditText popPassword;
    private PrintPosService printPosService;
    private QueryOrderData queryOrderData;
    private CatLoadingView mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refundable);
        //获取控件
        getView();
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
    private void getView() {
        titleBar = (TitleBar) findViewById(R.id.refunds_bar);
        btn_sure = (Button) findViewById(R.id.refundable_btn_sure);
        refunds_edittext = (ClearEditText) findViewById(R.id.refunds_edittext);
        show_refundable = (LinearLayout) findViewById(R.id.show_refundable);
        gone_refundable = (LinearLayout) findViewById(R.id.gone_refundable);
        mQRCodeView = (QRCodeView) findViewById(R.id.refunds_zxingview);
        mView = new CatLoadingView();
        btn_sure.setOnClickListener(todo);
        mQRCodeView.setDelegate(this);
    }

    View.OnClickListener todo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showpopupWindow(v);// 显示PopupWindow
        }
    };

    private void showpopupWindow(View v) {
        refundsSn = refunds_edittext.getText().toString();
        LayoutInflater layoutInflater = LayoutInflater.from(RefundActivity.this);
        View view = layoutInflater.inflate(R.layout.refundpopupwindow, null);
        popupWindow = new PopupWindow(view, 500, 500, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        TextView poptxt = (TextView) view.findViewById(R.id.poprefundssn);
        TextView poptitle = (TextView) view.findViewById(R.id.pop_title);
        popPassword = (EditText) view.findViewById(R.id.pop_edt);
        Button popbtn = (Button) view.findViewById(R.id.pop_btn);
        poptxt.setText(refundsSn);
        poptitle.setText("当前单号是");

        popbtn.setOnClickListener(doit);
        // PopupWindow弹出位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        backgroundAlpha(0.7f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });
    }

    View.OnClickListener doit = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            foresult();
        }
    };

    View.OnClickListener doPrint = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.query_popsure)

            {
                popupWindow.dismiss();
            } else

            {
                printPosService.printQueryOrder(queryOrderData);
            }
        }


    };

    private void showPrintfpopupWindow(View v, QueryOrderData data) {
        queryOrderData = data;
        LayoutInflater layoutInflater = LayoutInflater.from(RefundActivity.this);
        View view = layoutInflater.inflate(R.layout.querypopupwindow, null);
        popupWindow = new PopupWindow(view, 500, 700, true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        //这里获取悬浮框的控件
        TextView jineTextView = (TextView) view.findViewById(R.id.pop_jine);
        TextView orderSnTextView = (TextView) view.findViewById(R.id.order_sn_text);
//        TextView tradeNoTextView = (TextView)view.findViewById(R.id.trade_no_text);
        TextView amountTextView = (TextView) view.findViewById(R.id.pop_amount);
        TextView payTimeTextView = (TextView) view.findViewById(R.id.pay_time_text);
        TextView payTypeTextView = (TextView) view.findViewById(R.id.pay_type_text);
        TextView payStatusTextView = (TextView) view.findViewById(R.id.pay_status_text);
        orderSnTextView.setText(data.getOrderSn());

        amountTextView.setText(data.getAmount());
        payTimeTextView.setText(DateUtil.TimestampToString(Long.valueOf(data.getModifyDate()), DateStyle.YYYY_MM_DD_HH_MM_SS_EN));
        payTypeTextView.setText(data.getPayTypeTxt());
        payStatusTextView.setText("已退款");
        jineTextView.setText("退款金额");

        //下面的那些控件你自己获取一下
        Button querypopbtnsure = (Button) view.findViewById(R.id.query_popsure);//左边确定按钮
        Button querypopprint = (Button) view.findViewById(R.id.query_popprint);//右边打印按钮
        querypopbtnsure.setOnClickListener(doPrint);//确定按钮
        querypopprint.setOnClickListener(doPrint);//打印按钮
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

    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp);
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
            RefundActivity.this.finish();
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.changeToScanBarcodeStyle();
        mQRCodeView.startSpotDelay(100);//延迟100毫秒开始识别
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        foresult();
        showToast(result);
        refundsSn = result;
        refunds_edittext.setText(refundsSn);
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

    @Override
    protected void onResume() {
        super.onResume();
        //绑定设备开启
        bindDeviceService();
        //实现printer方法
        printPosService = new PrintPosService(this);
    }

    private void foresult() {
        showDialog();
        refundService = new RefundService();
        refundService.refund(refundsSn, popPassword.getText().toString(), new CommonCallback<NetworkResult<QueryOrderData>>() {

            @Override
            public void onSuccess(NetworkResult<QueryOrderData> data) {
                mView.dismiss();
                if (APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())) {
                    showPrintfpopupWindow(btn_sure, data.getData());
                    //退款成功跳到新页面
                } else {
                    showToast(data.getMsg());
                }
            }

            @Override
            public void onFailure(String error_code, String error_message) {
                mView.dismiss();
                showToast("返回错误的结果");
            }
        });
//            showToast("点击了密码");
        popupWindow.dismiss();
    }
    public void showDialog() {
        mView.show(getSupportFragmentManager(), "");
    }
}
