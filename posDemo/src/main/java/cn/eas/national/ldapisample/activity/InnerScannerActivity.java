package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.presenter.IInnerScannerPresenter;
import cn.eas.national.ldapisample.presenter.impl.InnerScannerPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class InnerScannerActivity extends BaseActivity {
    private static final int SCAN_TIMEOUT = 10;

	private IInnerScannerPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.eas.national.ldapisample.R.layout.inner_scanner);

		findViewById(cn.eas.national.ldapisample.R.id.btnStartScanner).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				displayInfo(" ------------ start scan ------------ ");
                presenter.startScan(SCAN_TIMEOUT);
			}
		});
		
		findViewById(cn.eas.national.ldapisample.R.id.btnStopScanner).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				displayInfo(" ------------ stop scan ------------ ");
				presenter.stopScan();
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
        presenter = new InnerScannerPresenterImpl(this);
    }
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
		String desc = "可用于带内置扫码硬件的终端，如P990/P960等设备。";
		return desc;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_FOCUS) {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				presenter.startScan(SCAN_TIMEOUT);
			} else {
				presenter.stopScan();
			}
			return true;
		} else {
			return super.dispatchKeyEvent(event);
		}
	}
}
