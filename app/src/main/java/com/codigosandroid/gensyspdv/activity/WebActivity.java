package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.fragment.WebFragment;


/**
 * Created by Tiago on 12/09/2017.
 */

public class WebActivity extends BaseActivity {

    String site;
    String privacidade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        site = getIntent().getStringExtra("site");
        privacidade = getIntent().getStringExtra("privacidade");

        if (site != null) {
            getSupportActionBar().setTitle(site);
        } else if (privacidade != null) {
            getSupportActionBar().setTitle(privacidade);
        }

        if (savedInstanceState == null) {
            WebFragment webFragment = new WebFragment();
            webFragment.setArguments(getIntent().getExtras());
            replaceFragment(R.id.container_fragment, webFragment);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: finish(); break;
        }
        return super.onOptionsItemSelected(item);

    }



}
