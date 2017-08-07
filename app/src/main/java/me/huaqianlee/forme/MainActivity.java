package me.huaqianlee.forme;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import me.huaqianlee.forme.util.LogUtil;

public class MainActivity extends BaseActivity {

    private final static String TAG = "ForMe.MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        LogUtil.i(TAG,getString(R.string.test_log));
    }


}
