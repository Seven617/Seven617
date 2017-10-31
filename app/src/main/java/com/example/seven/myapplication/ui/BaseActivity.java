package com.example.seven.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.seven.myapplication.R;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsae);
    }
    //Toast及时反应
    private Toast mToast;

    public void ShowToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
