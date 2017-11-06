package cn.eas.national.ldapisample.activity;

import android.os.Bundle;
import android.view.Menu;

/**
 * There are all mag card operations samples in this Activity.
 * @author caizl
 *
 */
public class SubscreenActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.eas.national.ldapisample.R.layout.subsreen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(cn.eas.national.ldapisample.R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		displayInfo("这是主屏应用将其中一个activity投射到客屏的示例");
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public String getModuleDescription() {
		String desc = "投影到客屏的示例activity";
		return desc;
	}
}
