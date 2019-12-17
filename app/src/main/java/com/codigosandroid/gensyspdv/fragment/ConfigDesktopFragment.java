package com.codigosandroid.gensyspdv.fragment;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 27/12/2017.
 */

public class ConfigDesktopFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String TAG = ConfigDesktopFragment.class.getSimpleName();

    private EditTextPreference host, company, user, db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuracoes_desktop);
        inicializar();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case "host": host.setSummary(newValue.toString()); break;
            case "company": company.setSummary(newValue.toString()); break;
            case "db": db.setSummary(newValue.toString()); break;
            case "user": user.setSummary(newValue.toString()); break;
        }
        return true;
    }

    private void inicializar() {

        String host_summary = getString(R.string.pref_default_host_summary),
                company_summary = getString(R.string.pref_default_company_summary),
                user_summary = getString(R.string.pref_default_user_summary),
                db_summary = getString(R.string.pref_default_db_summary);

        try {
            // Host
            host = (EditTextPreference) findPreference(getString(R.string.pref_host_key));
            host_summary = host != null ? host.getText() : host_summary;
            if (host != null) {
                host.setSummary(host_summary);
                host.setOnPreferenceChangeListener(this);
            }
            // Company
            company = (EditTextPreference) findPreference(getString(R.string.pref_company_key));
            company_summary = company != null ? company.getText() : company_summary;
            if (company != null) {
                company.setSummary(company_summary);
                company.setOnPreferenceChangeListener(this);
            }
            // Db
            db = (EditTextPreference) findPreference(getString(R.string.pref_db_key));
            db_summary = db != null ? db.getText() : db_summary;
            if (db != null) {
                db.setSummary(db_summary);
                db.setOnPreferenceChangeListener(this);
            }
            // User
            user = (EditTextPreference) findPreference(getString(R.string.pref_user_key));
            user_summary = user != null ? user.getText() : user_summary;
            if (user != null) {
                user.setSummary(user_summary);
                user.setOnPreferenceChangeListener(this);
            }
        } catch (NullPointerException e) {
            LogUtil.error(TAG, e.getMessage(), e);
        }

    }

}
