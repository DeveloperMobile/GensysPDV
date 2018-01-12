package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.fragment.DetalheVendaFragment;
import com.codigosandroid.gensyspdv.fragment.MainFragment;
import com.codigosandroid.gensyspdv.venda.PyVenda;

public class DetalheVendaActivity extends BaseActivity {

    PyVenda pyVenda = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_venda);
        setUpToolbar();
        //pyVenda = (PyVenda) getIntent().getSerializableExtra("item");
        if (savedInstanceState == null) {
            //DetalheVendaFragment dvf = new DetalheVendaFragment();
            //dvf.setArguments(getIntent().getExtras());
            replaceFragment(R.id.container_fragment, new DetalheVendaFragment());
        }
    }

}
