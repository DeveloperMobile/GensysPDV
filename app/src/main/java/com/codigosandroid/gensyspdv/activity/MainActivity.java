package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;

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

}
