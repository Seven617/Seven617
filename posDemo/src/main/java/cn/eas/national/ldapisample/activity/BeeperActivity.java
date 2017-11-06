package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.presenter.IBeeperPresenter;
import cn.eas.national.ldapisample.presenter.impl.BeeperPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class BeeperActivity extends BaseActivity {
	private IBeeperPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beeper);

		findViewById(R.id.btnStartBeep).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ start beep  ------------ ");
				presenter.startBeep();
			}
		});
		findViewById(R.id.btnStopBeep).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ stop beep  ------------ ");
				presenter.stopBeep();
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
		presenter = new BeeperPresenterImpl(this);
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
        String desc = "支持具备蜂鸣器设备的终端，如A8/P990/W280PV3，C10/P960/W280P/W280PV2不支持该模块。";
		return null;
	}
}
