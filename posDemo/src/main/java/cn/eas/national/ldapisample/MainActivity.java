package cn.eas.national.ldapisample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.eas.national.ldapisample.activity.AlgorithmActivity;
import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.activity.BeeperActivity;
import cn.eas.national.ldapisample.activity.C10SubscreenDeviceActivity;
import cn.eas.national.ldapisample.activity.CameraScannerActivity;
import cn.eas.national.ldapisample.activity.CashBoxActivity;
import cn.eas.national.ldapisample.activity.ICCpuCardActivity;
import cn.eas.national.ldapisample.activity.IDCardActivity;
import cn.eas.national.ldapisample.activity.InnerScannerActivity;
import cn.eas.national.ldapisample.activity.LedActivity;
import cn.eas.national.ldapisample.activity.MagCardActivity;
import cn.eas.national.ldapisample.activity.MifareCardActivity;
import cn.eas.national.ldapisample.activity.PinpadActivity;
import cn.eas.national.ldapisample.activity.PrinterActivity;
import cn.eas.national.ldapisample.activity.PsamCardActivity;
import cn.eas.national.ldapisample.activity.RFCpuCardActivity;
import cn.eas.national.ldapisample.activity.ScannerActivity;
import cn.eas.national.ldapisample.activity.SerialPortActivity;
import cn.eas.national.ldapisample.activity.SystemDeviceActivity;
import cn.eas.national.ldapisample.data.Constants;

public class MainActivity extends BaseActivity {
    private List<DeviceModule> listDevices = new ArrayList<DeviceModule>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.eas.national.ldapisample.R.layout.activity_main);
        initListView();
    }

    private void initListView(){
        ListView list = (ListView) findViewById(cn.eas.national.ldapisample.R.id.listView1);
        initData();

        DeviceModuleAdapter adapter = new DeviceModuleAdapter(this, cn.eas.national.ldapisample.R.layout.listview_item, listDevices);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                itemClickListener(listDevices.get(position).type);
            }
        });
    }

    private void initData() {
        listDevices.clear();
        listDevices.add(new DeviceModule("磁条卡读卡器", Constants.Device.DEVICE_MODULE_MAG_CARD));
        listDevices.add(new DeviceModule("接触式CPU卡读卡器", Constants.Device.DEVICE_MODULE_IC_CPU_CARD));
//        listDevices.add(new DeviceModule("同步卡读卡器", Constants.Device.DEVICE_MODULE_SYNC_CARD));
        listDevices.add(new DeviceModule("PSAM卡读卡器", Constants.Device.DEVICE_MODULE_PSAM_CARD));
        listDevices.add(new DeviceModule("非接CPU卡读卡器", Constants.Device.DEVICE_MODULE_RF_CPU_CARD));
        listDevices.add(new DeviceModule("非接MifareOne卡读卡器", Constants.Device.DEVICE_MODULE_RF_M1_CARD));
        listDevices.add(new DeviceModule("二代证读卡器", Constants.Device.DEVICE_MODULE_ID_CARD));
        listDevices.add(new DeviceModule("打印机", Constants.Device.DEVICE_MODULE_PRINTER));
        listDevices.add(new DeviceModule("密码键盘", Constants.Device.DEVICE_MODULE_PINPAD));
        listDevices.add(new DeviceModule("串口", Constants.Device.DEVICE_MODULE_SERIALPORT));
        listDevices.add(new DeviceModule("摄像头扫码器", Constants.Device.DEVICE_MODULE_CAMERA_SCANNER));
        listDevices.add(new DeviceModule("内置扫码器", Constants.Device.DEVICE_MODULE_INNERSCANNER));
        listDevices.add(new DeviceModule("外接扫码枪", Constants.Device.DEVICE_MODULE_SCANNER));
        listDevices.add(new DeviceModule("Led灯", Constants.Device.DEVICE_MODULE_LED));
        listDevices.add(new DeviceModule("蜂鸣器", Constants.Device.DEVICE_MODULE_BEEPER));
        listDevices.add(new DeviceModule("钱箱", Constants.Device.DEVICE_MODULE_CASHBOX));
//        listDevices.add(new DeviceModule("Modem", Constants.Device.DEVICE_MODULE_MODEM));
        listDevices.add(new DeviceModule("C10客屏设备", Constants.Device.DEVICE_MODULE_C10_SUBSCREEN_DEVICE));
        listDevices.add(new DeviceModule("算法示例", Constants.Device.DEVICE_MODULE_ALGORITHM));
        listDevices.add(new DeviceModule("系统接口", Constants.Device.DEVICE_MODULE_SYSTEM));
    }

    private void itemClickListener(int index) {
        switch (index){
            case Constants.Device.DEVICE_MODULE_MAG_CARD:
                startActivity(MagCardActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_IC_CPU_CARD:
                startActivity(ICCpuCardActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_SYNC_CARD:
//                startActivity(SyncCardActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_PSAM_CARD:
                startActivity(PsamCardActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_RF_CPU_CARD:
                startActivity(RFCpuCardActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_RF_M1_CARD:
                startActivity(MifareCardActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_ID_CARD:
                startActivity(IDCardActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_PRINTER:
                startActivity(PrinterActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_PINPAD:
                startActivity(PinpadActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_SERIALPORT:
                startActivity(SerialPortActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_CAMERA_SCANNER:
                startActivity(CameraScannerActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_INNERSCANNER:
                startActivity(InnerScannerActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_SCANNER:
                startActivity(ScannerActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_LED:
                startActivity(LedActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_BEEPER:
                startActivity(BeeperActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_CASHBOX:
                startActivity(CashBoxActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_MODEM:
//                startActivity(ModemActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_C10_SUBSCREEN_DEVICE:
                startActivity(C10SubscreenDeviceActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_ALGORITHM:
                startActivity(AlgorithmActivity.class);
                break;
            case Constants.Device.DEVICE_MODULE_SYSTEM:
                startActivity(SystemDeviceActivity.class);
                break;
            default:
                break;
        }
    }

    public void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public String getModuleDescription() {
        return null;
    }

    class DeviceModuleAdapter extends ArrayAdapter<DeviceModule> {
        private Context context;
        private int resourceId;
        private List<DeviceModule> list;

        public DeviceModuleAdapter(Context context, int resourceId, List<DeviceModule> list) {
            super(context, resourceId, list);
            this.context = context;
            this.resourceId = resourceId;
            this.list = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(resourceId, null);
                TextView tvName = (TextView) convertView.findViewById(cn.eas.national.ldapisample.R.id.ItemText);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.tvName = tvName;
                convertView.setTag(viewHolder);
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            TextView tvName = viewHolder.tvName;
            tvName.setText(list.get(position).name);
            return convertView;
        }
    }

    class ViewHolder {
        public TextView tvName;
    }

    class DeviceModule {
        public String name;
        public int type;
        public DeviceModule(String name, int type) {
            this.name = name;
            this.type = type;
        }
    }
}
