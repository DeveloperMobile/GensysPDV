package com.codigosandroid.gensyspdv.activity;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.codigosandroid.gensyspdv.R;

/**
 * Created by Tiago on 26/12/2017.
 */

public class BaseActivity extends com.codigosandroid.utils.activity.BaseActivity {

    /* Configura a Toolbar */
    protected void setUpToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }


    protected void replaceFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

}
