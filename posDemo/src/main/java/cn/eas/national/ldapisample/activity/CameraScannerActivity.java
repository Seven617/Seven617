package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.presenter.ICameraScannerPresenter;
import cn.eas.national.ldapisample.presenter.impl.CameraScannerPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class CameraScannerActivity extends BaseActivity {
	private ICameraScannerPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_scanner);

		findViewById(R.id.btnFrontScan).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ front scan  ------------ ");
				presenter.startScan(Constants.Scanner.CAMERA_FRONT);
			}
		});

		findViewById(R.id.btnBackScan).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ back scan  ------------ ");
				presenter.startScan(Constants.Scanner.CAMERA_BACK);
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
        presenter = new CameraScannerPresenterImpl(this);
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
		String desc = "使用摄像头进行扫码。A8终端有前后置摄像可进行前后置不同的扫码，对于诸如A7/W280P/C10等终端，"
                + "因只有后置，故只能使用后置扫码功能。另外可进行扫码的前提条件是终端已集成扫码库。";
		return desc;
	}
}
