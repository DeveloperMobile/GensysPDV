package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        if (savedInstanceState == null) {
            replaceFragment(R.id.container_fragment, new MainFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync: break;
            case R.id.menu_settings: break;
            case R.id.menu_info: break;
            case R.id.menu_exit: finish(); break;
        }
        return super.onOptionsItemSelected(item);

    }
}
