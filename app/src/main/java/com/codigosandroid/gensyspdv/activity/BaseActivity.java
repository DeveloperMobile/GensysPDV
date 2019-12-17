package com.codigosandroid.gensyspdv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.cloud.GeniusWeb;
import com.codigosandroid.gensyspdv.cloud.ServiceCloud;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.fragment.dialog.DialogSyncFragment;
import com.codigosandroid.gensyspdv.usuario.Usuario;
import com.codigosandroid.gensyspdv.utils.AsyncListener;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.gensyspdv.venda.PyVenda;
import com.codigosandroid.gensyspdv.venda.ServicePyVenda;
import com.codigosandroid.utils.activity.DebugActivity;
import com.codigosandroid.utils.utils.AlertUtil;
import com.codigosandroid.utils.utils.LogUtil;
import com.codigosandroid.utils.utils.NavDrawerUtil;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Tiago on 26/12/2017.
 */

public class BaseActivity extends com.codigosandroid.utils.activity.BaseActivity {

    protected DrawerLayout drawerLayout;
    protected Handler handler = new Handler();
    protected String[] syncList = new String[]{
            Constantes.CLOUD,
            Constantes.G_WEB
    };

    /* Configura a Toolbar */
    protected void setUpToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    /* Configura o NavDrawer */
    protected void setUpNavDrawer() {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // Ícone do menu nav drawer
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            if (navigationView != null && drawerLayout != null) {
                /* Atualiza os dados do header do Navigation View */
                try {
                    Usuario usuario = ServiceConfiguracoes.loadOperadorFromJSON(this);
                    NavDrawerUtil.setHeaderValues(navigationView, R.mipmap.ic_launcher_round,
                            usuario.getApelido(),
                            usuario.getEmail());
                /* Trata os eventos do menu */
                    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        /* Seleciona a linha */
                            item.setChecked(true);
                        /* Fecha o menu */
                            drawerLayout.closeDrawers();
                        /* Trata o evento do menu */
                            onNavDrawerItemSelected(item);
                            return true;
                        }
                    });
                } catch (FileNotFoundException e) {
                    LogUtil.error(DebugActivity.TAG, e.getMessage(), e);
                }
            }

        }

    }

    /* Trata os eventos do nav drawer */
    private void onNavDrawerItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_vendas:
                List<PyVenda> pyVendas = ServicePyVenda.getAllPyVenda(this);
                if (pyVendas.isEmpty()) {
                    AlertUtil.alert(this, "Aviso", "Nenhuma venda registrada!");
                } else {
                    startActivity(new Intent(this, VendasActivity.class));
                }
                break;
            case R.id.nav_item_reverse: break;
            case R.id.nav_item_reprint: break;
            case R.id.nav_item_sair: finish(); break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout != null) {
                    openDrawer();
                }
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    /* Abre o menu lateral */
    protected void openDrawer() {

        if (drawerLayout != null) {

            drawerLayout.openDrawer(GravityCompat.START);

        }

    }

    /* Fecha o menu lateral */
    protected void closeDrawer() {

        if (drawerLayout != null) {

            drawerLayout.closeDrawer(GravityCompat.START);

        }

    }

    protected void replaceFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    protected void replaceFragment(int container, android.app.Fragment fragment) {
        getFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    protected void startHomeScreen() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void startMainScreen(Context context, Class c) {
        Intent intent = new Intent(context, c);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public class SyncCloudTask extends AsyncTask<String, Void, Void> {

        private AsyncListener asyncListener;

        public SyncCloudTask(AsyncListener asyncListener) {

            this.asyncListener = asyncListener;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogSyncFragment.showDialog(getSupportFragmentManager());
        }

        @Override
        protected Void doInBackground(String... strings) {

            asyncListener.syncBackground();
            return null;

        }

        @Override
        protected void onPostExecute(Void avoid) {

            asyncListener.syncPostExecute();
            DialogSyncFragment.closeDialog(getSupportFragmentManager());

        }

    }

    protected void status(final String status) {

        handler.post(new Runnable() {

            @Override
            public void run() {

                DialogSyncFragment.lbStatus.setText(status);

            }

        });

    }

    /* Busca as configurações na nuvem */
    protected Cloud syncCloud(String msg, String loginEmail) {
        status(msg);
        return ServiceCloud.getCloud(loginEmail);
    }

    /* Busca as configuracoes geniusWeb */
    protected GeniusWeb syncGeniusWeb(String msg) {
        try {
            status(msg);
            return ServiceCloud.getGeniusWeb(this);
        } catch (FileNotFoundException e) {
            snack(null, "Arquivo não encontrado", null);
        }
        return new GeniusWeb();
    }

}
