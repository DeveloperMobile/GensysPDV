package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.fragment.SobreFragment;

public class SobreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            replaceFragment(R.id.container_fragment, new SobreFragment());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_sobre, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: finish(); break;
        }
        return super.onOptionsItemSelected(item);

    }

}
