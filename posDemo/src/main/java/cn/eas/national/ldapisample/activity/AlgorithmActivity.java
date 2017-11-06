package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.presenter.IAlgorithmPresenter;
import cn.eas.national.ldapisample.presenter.impl.AlgorithmPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class AlgorithmActivity extends BaseActivity {
	private IAlgorithmPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.algorithm);

		findViewById(R.id.btnCalcData).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ calculate data  ------------ ");
				presenter.calcData();
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
		presenter = new AlgorithmPresenterImpl(this);
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
        String desc = "该模块列举一般常用的几种算法，如：AES/MAC/TDES/SM4/SHA1/SHA256/RSA等。";
		return null;
	}
}
