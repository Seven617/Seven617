package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.presenter.impl.ScannerPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class ScannerActivity extends BaseActivity {
	private ScannerPresenterImpl presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.eas.national.ldapisample.R.layout.scanner);

		findViewById(cn.eas.national.ldapisample.R.id.btnStartBrScanner).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ start br scan  ------------ ");
				presenter.startBrScan();
			}
		});

		findViewById(cn.eas.national.ldapisample.R.id.btnStopBrScanner).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ stop br scan  ------------ ");
				presenter.stopBrScan();
			}
		});

		findViewById(cn.eas.national.ldapisample.R.id.btnStartScanner).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ start scan  ------------ ");
				presenter.startScan();
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
		presenter = new ScannerPresenterImpl(this);
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
		String desc = "该模块针对外接扫码枪设备。";
		return desc;
	}
}
