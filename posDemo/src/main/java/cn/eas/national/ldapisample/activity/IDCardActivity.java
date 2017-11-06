package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.presenter.IIDCardPresenter;
import cn.eas.national.ldapisample.presenter.impl.IDCardPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class IDCardActivity extends BaseActivity {
	private IIDCardPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idcard);

		findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ start search  ------------ ");
				presenter.searchCard();
			}
		});
		
		findViewById(R.id.btnStopSearch).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ stop search ------------ ");
				presenter.stopSearch();
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
		presenter = new IDCardPresenterImpl(this);
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
		String desc = "该模块可用于已集成SAMV模块的终端。目前可适配W280P终端，其他终端暂不支持。";
		return desc;
	}
}
