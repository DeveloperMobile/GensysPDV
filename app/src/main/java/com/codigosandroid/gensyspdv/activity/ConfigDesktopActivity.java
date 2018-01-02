package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.fragment.ConfigDesktopFragment;

public class ConfigDesktopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_desktop);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            replaceFragment(R.id.container_fragment, new ConfigDesktopFragment());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startMainScreen(this, MainActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        startMainScreen(this, MainActivity.class);
    }

}
