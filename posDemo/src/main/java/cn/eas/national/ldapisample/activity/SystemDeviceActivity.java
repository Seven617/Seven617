package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.presenter.ISystemDevicePresenter;
import cn.eas.national.ldapisample.presenter.impl.SystemDevicePresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class SystemDeviceActivity extends BaseActivity {
	private ISystemDevicePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system);

		findViewById(R.id.btnUpdateDatetime).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ update datetime  ------------ ");
				presenter.updateTime();
			}
		});
		findViewById(R.id.btnReboot).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ reboot  ------------ ");
				presenter.reboot();
			}
		});
		findViewById(R.id.btnDeviceInfo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ get device info  ------------ ");
				presenter.getDeviceInfo();
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
		presenter = new SystemDevicePresenterImpl(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
        String desc = "获取设备相关信息模块，包括序列号等，以及对设备进行重启和更新时间操作。";
		return null;
	}
}
