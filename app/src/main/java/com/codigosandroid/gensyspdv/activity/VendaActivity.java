package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.fragment.VendaFragment;

public class VendaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);
        setUpToolbar();
        setUpNavDrawer();

        if (savedInstanceState == null) {

            replaceFragment(R.id.container_fragment, new VendaFragment());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_venda, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home: startHomeScreen(); break;
        }
        return super.onOptionsItemSelected(item);

    }

}
