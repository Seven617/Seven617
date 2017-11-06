package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.presenter.ILedPresenter;
import cn.eas.national.ldapisample.presenter.impl.LedPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class LedActivity extends BaseActivity {
    private static final int FLAG_LED_FLASH = 0;
    private static final int FLAG_LED_STOP = 1;

	private ILedPresenter presenter;
	private Button btnLedFlash;
    private RadioGroup rgDeviceType;
    private RadioButton rbInner, rbExternal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.led);

        rbInner = (RadioButton) findViewById(R.id.rbInner);
        rbExternal = (RadioButton) findViewById(R.id.rbExternal);

        rgDeviceType = (RadioGroup) findViewById(R.id.rgDeviceType);
        rgDeviceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                if (id == rbInner.getId()) {
                    presenter = new LedPresenterImpl(LedActivity.this, Constants.Led.DEVICE_INNER);
                } else {
                    presenter = new LedPresenterImpl(LedActivity.this, Constants.Led.DEVICE_EXTERNAL);
                }
            }
        });

        btnLedFlash = (Button) findViewById(R.id.btnLedFlash);
        btnLedFlash.setTag(FLAG_LED_STOP);
		btnLedFlash.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                int flag = (Integer) btnLedFlash.getTag();
                if (flag == FLAG_LED_FLASH) {
                    btnLedFlash.setTag(FLAG_LED_STOP);
                    btnLedFlash.setText("Led Flash");
                    displayInfo(" ------------ stop  ------------ ");
                    presenter.stop();
                } else {
                    btnLedFlash.setTag(FLAG_LED_FLASH);
                    btnLedFlash.setText("Stop");
                    displayInfo(" ------------ led flash  ------------ ");
                    presenter.flash();
                }
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		bindDeviceService();
        if (rbInner.isChecked()) {
            presenter = new LedPresenterImpl(this, Constants.Led.DEVICE_INNER);
        } else {
            presenter = new LedPresenterImpl(this, Constants.Led.DEVICE_EXTERNAL);
        }
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// 打开led灯时会同时打开非接载波，为避免电耗过快且机具发烫，在不使用led时应对非接设备下电
		try {
            if (rbInner.isChecked()) {
                RFCardReader.getInstance().halt();
            } else {
                RFCardReader.getOtherInstance("EXTRFCARD").halt();
            }
		} catch (RequestException e) {
			e.printStackTrace();
		}
		unbindDeviceService();
	}

    @Override
    public String getModuleDescription() {
        String desc = "针对终端上已经非接模块可直接显示，否则需外接设备进行显示。"
                + "C10/W280P等终端外接密码键盘使用时，非接操作实例初始化时传入设备名称为“EXTRFCARD”。";
        return desc;
    }
}
