package com.codigosandroid.gensyspdv.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.cloud.Cloud;
import com.codigosandroid.gensyspdv.cloud.GeniusWeb;
import com.codigosandroid.gensyspdv.configuracoes.Configuracoes;
import com.codigosandroid.gensyspdv.configuracoes.ServiceConfiguracoes;
import com.codigosandroid.gensyspdv.fragment.ConfigCloudFragment;
import com.codigosandroid.gensyspdv.utils.AsyncListener;
import com.codigosandroid.gensyspdv.utils.Constantes;
import com.codigosandroid.utils.utils.AlertUtil;
import com.codigosandroid.utils.utils.FileUtil;
import com.codigosandroid.utils.utils.IOUtil;
import com.codigosandroid.utils.utils.SharedUtil;

public class ConfigCloudActivity extends BaseActivity {

    private Cloud cloud = new Cloud();
    private GeniusWeb geniusWeb = new GeniusWeb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_cloud);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            replaceFragment(R.id.container_fragment, new ConfigCloudFragment());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                validaConfiguracoes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        validaConfiguracoes();
    }

    private void sync() {
        new SyncCloudTask(new AsyncListener() {
            @Override
            public void syncBackground() {
                for (int i = 0; i < syncList.length; i++) {
                    if (syncList[i].equals(Constantes.CLOUD)) {
                        cloud = syncCloud("Sincronizando configurações na nuvem...",
                                SharedUtil.getString(ConfigCloudActivity.this, getString(R.string.pref_email_key)));
                        if (ServiceConfiguracoes.isPreferencesCloud(ConfigCloudActivity.this)) {
                            ServiceConfiguracoes.deleteJson(ConfigCloudActivity.this, Constantes.FILE_CLOUD_JSON);
                            ServiceConfiguracoes.saveJson(ConfigCloudActivity.this, Constantes.FILE_CLOUD_JSON, cloud);
                        } else  {
                            ServiceConfiguracoes.saveJson(ConfigCloudActivity.this, Constantes.FILE_CLOUD_JSON, cloud);
                        }
                    } else if (syncList[i].equals(Constantes.G_WEB)) {
                            geniusWeb = syncGeniusWeb("Validando configurações web...");
                            ServiceConfiguracoes.saveJson(ConfigCloudActivity.this, Constantes.FILE_GW_JSON, geniusWeb);
                    }
                }
            }

            @Override
            public void syncPostExecute() {

                Cloud cloud = ServiceConfiguracoes.loadCloudFromJSON(ConfigCloudActivity.this);
                if (ServiceConfiguracoes.isPreferencesCloud(ConfigCloudActivity.this) && cloud != null) {
                    returnToMain();
                } else {
                    AlertUtil.alert(ConfigCloudActivity.this, "Aviso",
                            "Erro ao sincronizar configurações da nuvem!");
                }
            }
        }).execute(syncList);
    }

    private void returnToMain() {
        finish();
        startMainScreen(ConfigCloudActivity.this, MainActivity.class);
    }

    private boolean verificaConfiguracoes() {

        String email = SharedUtil.getString(this, getString(R.string.pref_email_key));
        return email.equals("");

    }

    protected void validaConfiguracoes() {
        if (!verificaConfiguracoes()) {
            try {
                cloud = ServiceConfiguracoes.loadCloudFromJSON(this);
                if (!SharedUtil.getString(this, getString(R.string.pref_email_key)).equals(cloud.getLoginEmail())) {
                    sync();
                } else {
                    returnToMain();
                }
            } catch (Exception e) {
                sync();
            }
        } else {
            AlertUtil.alert(this, "Aviso",
                    "Nenhuma configuração foi efetuada, sair assim mesmo?", 0, 0,
                    new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, null);
        }
    }

}
