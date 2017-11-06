package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.presenter.IPsamCardPresenter;
import cn.eas.national.ldapisample.presenter.impl.PsamCardPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class PsamCardActivity extends BaseActivity {
	private RadioButton rbSlot1, rbSlot2, rbSlot3, rbSlot4;
	private IPsamCardPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.eas.national.ldapisample.R.layout.psamcard);

		rbSlot1 = (RadioButton) findViewById(cn.eas.national.ldapisample.R.id.rbSlot1);
		rbSlot2 = (RadioButton) findViewById(cn.eas.national.ldapisample.R.id.rbSlot2);
		rbSlot3 = (RadioButton) findViewById(cn.eas.national.ldapisample.R.id.rbSlot3);
		rbSlot4 = (RadioButton) findViewById(cn.eas.national.ldapisample.R.id.rbSlot4);

		findViewById(R.id.btnCardPower).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ card power  ------------ ");
				int slot = 1;
				if (rbSlot2.isChecked()) {
					slot = 2;
				} else if (rbSlot3.isChecked()) {
					slot = 3;
				} else if (rbSlot4.isChecked()) {
					slot = 4;
				}
				presenter.cardPower(slot);
			}
		});
		findViewById(R.id.btnCardHalt).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ card halt ------------ ");
				presenter.cardHalt();
			}
		});
		findViewById(cn.eas.national.ldapisample.R.id.btnExist).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ exist ------------ ");
				presenter.exist();
			}
		});

		findViewById(cn.eas.national.ldapisample.R.id.btnExchangeApdu).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ exchange apdu ------------ ");
				presenter.exchangeApdu();
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
		presenter = new PsamCardPresenterImpl(this);
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	@Override
	public String getModuleDescription() {
		return null;
	}
}
