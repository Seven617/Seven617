package cn.eas.national.ldapisample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

import com.landicorp.android.eptapi.DeviceService;
import com.landicorp.android.eptapi.exception.ReloginException;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.exception.ServiceOccupiedException;
import com.landicorp.android.eptapi.exception.UnsupportMultiProcess;

import cn.eas.national.ldapisample.R;

/**
 * Base Activity.
 * Each code sample activity extends it.
 * @author caizl
 *
 */
public abstract class BaseActivity extends Activity implements cn.eas.national.ldapisample.activity.IDeviceView {
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isDeviceServiceLogined = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setModuleDescription();
    }

    protected boolean isDeviceServiceLogined() {
        return isDeviceServiceLogined;
    }

    /**
     * Run something on ui thread after milliseconds.
     *
     * @param r
     * @param delayMillis
     */
    public void runOnUiThreadDelayed(Runnable r, int delayMillis) {
        handler.postDelayed(r, delayMillis);
    }

    /**
     * To gain control of the device service,
     * you need invoke this method before any device operation.
     */
    public void bindDeviceService() {
        try {
            isDeviceServiceLogined = false;
            DeviceService.login(this);
            isDeviceServiceLogined = true;
        } catch (RequestException e) {
            // Rebind after a few milliseconds,
            // If you want this application keep the right of the device service
//			runOnUiThreadDelayed(new Runnable() {
//				@Override
//				public void run() {
//					bindDeviceService();
//				}
//			}, 300);
            e.printStackTrace();
        } catch (ServiceOccupiedException e) {
            e.printStackTrace();
        } catch (ReloginException e) {
            e.printStackTrace();
        } catch (UnsupportMultiProcess e) {
            e.printStackTrace();
        }
    }

    /**
     * Release the right of using the device.
     */
    public void unbindDeviceService() {
        DeviceService.logout();
        isDeviceServiceLogined = false;
    }

    /**
     * Get handler in the ui thread
     *
     * @return
     */
    public Handler getUIHandler() {
        return handler;
    }

    /**
     * All device operation result infomation will be displayed by this method.
     *
     * @param info
     */
    @Override
    public void displayInfo(final String info) {
        // 将显示的消息滚动到最底部
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        final TextView tvInfo = (TextView) findViewById(R.id.info_text);
        getUIHandler().post(new Runnable() {
            @Override
            public void run() {
                String text = tvInfo.getText().toString();
                if(text.isEmpty()) {
                    tvInfo.setText(info);
                } else {
                    tvInfo.setText(text + "\n" + info);
                }
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void setModuleDescription() {
        String desc = getModuleDescription();
        TextView tvDescriptionTitle = (TextView) findViewById(R.id.tvDescriptionTitle);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        TextView tvDescriptionSeperator = (TextView) findViewById(R.id.tvDescriptionSeperator);
        if (tvDescription != null && !TextUtils.isEmpty(desc)) {
            tvDescription.setText(desc);
        } else {
            tvDescriptionTitle.setVisibility(View.GONE);
            tvDescription.setVisibility(View.GONE);
            tvDescriptionSeperator.setVisibility(View.GONE);
        }
    }

    public abstract String getModuleDescription();
}
