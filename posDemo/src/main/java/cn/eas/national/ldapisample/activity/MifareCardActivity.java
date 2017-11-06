package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.utils.BytesBuffer;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.presenter.IMifareCardPresenter;
import cn.eas.national.ldapisample.presenter.impl.MifareCardPresenterImpl;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class MifareCardActivity extends BaseActivity {
	private static final int BLOCK_NO_AUTH = 0x11;

	private IMifareCardPresenter presenter;
	private RadioGroup rgDeviceType;
	private RadioButton rbInner, rbExternal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mifarecard);

		rbInner = (RadioButton) findViewById(R.id.rbInner);
		rbExternal = (RadioButton) findViewById(R.id.rbExternal);

		rgDeviceType = (RadioGroup) findViewById(R.id.rgDeviceType);
		rgDeviceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
				if (id == rbInner.getId()) {
					presenter = new MifareCardPresenterImpl(MifareCardActivity.this, Constants.RFCard.DEVICE_INNER);
				} else {
					presenter = new MifareCardPresenterImpl(MifareCardActivity.this, Constants.RFCard.DEVICE_EXTERNAL);
				}
			}
		});

		findViewById(R.id.btnCardPower).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ card power  ------------ ");
				presenter.cardPower();
			}
		});
		
		findViewById(R.id.btnCardHalt).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ card halt ------------ ");
                presenter.cardHalt();
			}
		});
        findViewById(R.id.btnExist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInfo(" ------------ exist ------------ ");
                presenter.exist();
            }
        });
		findViewById(R.id.btnAuthBlock).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ auth ------------ ");
				presenter.authBlock(MifareDriver.KEY_B, BLOCK_NO_AUTH, ByteUtil.hexString2Bytes("FFFFFFFFFFFF"));
			}
		});
		findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ read ------------ ");
				BytesBuffer buffer = new BytesBuffer();
				presenter.read(BLOCK_NO_AUTH, buffer);
			}
		});
		findViewById(R.id.btnWrite).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ write ------------ ");
				presenter.write(BLOCK_NO_AUTH, ByteUtil.hexString2Bytes("123456"));
			}
		});
		findViewById(R.id.btnAddValue).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ increase ------------ ");
				presenter.addValue(BLOCK_NO_AUTH, 1);
			}
		});
		findViewById(R.id.btnReduceValue).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ decrease ------------ ");
				presenter.reduceValue(BLOCK_NO_AUTH, 1);
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
			presenter = new MifareCardPresenterImpl(this, Constants.RFCard.DEVICE_INNER);
		} else {
			presenter = new MifareCardPresenterImpl(this, Constants.RFCard.DEVICE_EXTERNAL);
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
		String desc = "针对终端上已经非接模块可直接使用，否则需外接设备进行调用。使用外接设备时，"
				+ "RFCardReader实例化时需使用getOtherInstance()，传入设备名EXTRFCARD。"
				+ "且寻卡监听回调需使用OnSearchListenerEx。";
		return desc;
	}
}
