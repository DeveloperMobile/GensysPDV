package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.fragment.DetalheVendaFragment;
import com.codigosandroid.gensyspdv.fragment.MainFragment;
import com.codigosandroid.gensyspdv.venda.PyVenda;
import com.codigosandroid.utils.utils.LogUtil;

public class DetalheVendaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_venda);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //PyVenda pyVenda = (PyVenda) getIntent().getSerializableExtra("item");
        //toast(pyVenda.getDataEmissao());
        if (savedInstanceState == null) {
            DetalheVendaFragment detalheVendaFragment = new DetalheVendaFragment();
            detalheVendaFragment.setArguments(getIntent().getExtras());
            replaceFragment(R.id.container_fragment, detalheVendaFragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
