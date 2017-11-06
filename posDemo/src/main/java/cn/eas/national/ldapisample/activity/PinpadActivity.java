package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.presenter.IPinpadPresenter;
import cn.eas.national.ldapisample.presenter.impl.PinpadPresenterImpl;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class PinpadActivity extends BaseActivity {
	private IPinpadPresenter presenter;
    private RadioGroup rgDeviceType;
	private RadioButton rbInner, rbExternal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.eas.national.ldapisample.R.layout.pinpad);

		rbInner = (RadioButton) findViewById(R.id.rbInner);
		rbExternal = (RadioButton) findViewById(R.id.rbExternal);

        rgDeviceType = (RadioGroup) findViewById(R.id.rgDeviceType);
        rgDeviceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                if (id == rbInner.getId()) {
                    presenter = new PinpadPresenterImpl(PinpadActivity.this, Constants.Pinpad.DEVICE_INNER);
                } else {
                    presenter = new PinpadPresenterImpl(PinpadActivity.this, Constants.Pinpad.DEVICE_EXTERNAL);
                }
            }
        });

		findViewById(cn.eas.national.ldapisample.R.id.btnLoadKey).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ load key  ------------ ");
				presenter.loadKey();
			}
		});
		findViewById(cn.eas.national.ldapisample.R.id.btnInputPin).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ input pin ------------ ");
				presenter.startOnlinePinEntry("6214123443211234");
			}
		});
		findViewById(cn.eas.national.ldapisample.R.id.btnCalcMac).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ calc mac ------------ ");
                presenter.calcMac(ByteUtil.hexString2Bytes("11111111111111111111111111111111"));
			}
		});
		findViewById(cn.eas.national.ldapisample.R.id.btnEncryptTdData).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ encrypt td data ------------ ");
                presenter.encryptMagTrack(ByteUtil.hexString2Bytes("22222222222222222222222222222222"));
			}
		});
		findViewById(cn.eas.national.ldapisample.R.id.btnEncryptData).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ encrypt data ------------ ");
                presenter.encryptData(ByteUtil.hexString2Bytes("00000000000000000000000000000000"));
			}
		});
		findViewById(cn.eas.national.ldapisample.R.id.btnDecryptData).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ decrypt data ------------ ");
                presenter.decryptData(ByteUtil.hexString2Bytes("74D669C708972B1A74D669C708972B1A"));
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(cn.eas.national.ldapisample.R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		bindDeviceService();
        if (rbInner.isChecked()) {
            presenter = new PinpadPresenterImpl(this, Constants.Pinpad.DEVICE_INNER);
        } else {
            presenter = new PinpadPresenterImpl(this, Constants.Pinpad.DEVICE_EXTERNAL);
        }
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
		String desc = "针对终端上已经Pinpad模块可直接使用，否则需外接设备进行调用。使用内置键盘时，"
				+ "Pinpad实例化时需传入设备名IPP，使用外接设备时，Pinpad实例化时需传入设备名COM_EPP。";
		return desc;
	}
}
