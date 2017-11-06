package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.presenter.IPrinterPresenter;
import cn.eas.national.ldapisample.presenter.impl.PrinterPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class PrinterActivity extends BaseActivity {
	private IPrinterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer);

		findViewById(R.id.btnStartPrint).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ start print ------------ ");
				presenter.start();
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
		presenter = new PrinterPresenterImpl(this);
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
		String desc = "针对无打印机终端需使用外接打印机，如蓝牙打印机等。该示例针对内置打印机。";
		return desc;
	}
}
