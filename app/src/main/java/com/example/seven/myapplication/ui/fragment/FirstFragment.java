package com.example.seven.myapplication.ui.fragment;

import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.service.QueryOrderService;
import com.example.seven.myapplication.view.ClearEditText;

import java.util.ArrayList;
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
    private QRCodeView mQRCodeView;
    private QueryOrderService queryOrderService;


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


    }
    //btn按钮点击事件
    View.OnClickListener OK = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            queryOrderService =new QueryOrderService();
            orderSn = clearEditText.getText().toString();
            queryOrderService.queryByOrderNo(orderSn, new CommonCallback<NetworkResult<List<QueryOrderData>>>() {
                @Override
                public void onSuccess(NetworkResult<List<QueryOrderData>>  data) {
                    if(APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())){
                        showToast(data.getData().get(0).getAmount());
                    }

                }

                @Override
                public void onFailure(String error_code, String error_message) {

                }
            });
        }
    };

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
