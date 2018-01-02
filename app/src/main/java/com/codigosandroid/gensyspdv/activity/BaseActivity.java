package com.codigosandroid.gensyspdv.activity;

import android.content.Context;
import android.content.Intent;
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

    protected void replaceFragment(int container, android.app.Fragment fragment) {
        getFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    protected void startMainScreen(Context context, Class c) {
        Intent intent = new Intent(context, c);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
