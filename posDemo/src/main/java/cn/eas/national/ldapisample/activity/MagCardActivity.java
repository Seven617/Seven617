package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import cn.eas.national.ldapisample.R;
import cn.eas.national.ldapisample.presenter.IMagCardPresenter;
import cn.eas.national.ldapisample.presenter.impl.MagCardPresenterImpl;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class MagCardActivity extends BaseActivity {
	private IMagCardPresenter presenter;
    private Button btnSearch, btnStopSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magcard);

		btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSearch.setEnabled(false);
                btnStopSearch.setEnabled(true);
                displayInfo(" ------------ start search  ------------ ");
                presenter.searchCard();
            }
        });
		btnStopSearch = (Button) findViewById(R.id.btnStopSearch);
        btnStopSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnSearch.setEnabled(true);
                btnStopSearch.setEnabled(false);
                displayInfo(" ------------ stop search ------------ ");
                presenter.stopSearch();
            }
        });
        btnStopSearch.setEnabled(false);
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
        presenter = new MagCardPresenterImpl(this);
    }
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	public void finshSwipeCard() {
		btnSearch.setEnabled(true);
        btnStopSearch.setEnabled(false);
	}

    @Override
    public String getModuleDescription() {
        return null;
    }
}
