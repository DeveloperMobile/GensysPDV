package com.codigosandroid.gensyspdv.fragment;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.utils.utils.LogUtil;

/**
 * Created by Tiago on 27/12/2017.
 */

public class ConfigCloudFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String TAG = ConfigCloudFragment.class.getSimpleName();

    private EditTextPreference email, company;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuracoes_cloud);
        inicializar();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case "email": email.setSummary(newValue.toString()); break;
            case "company_cloud": company.setSummary(newValue.toString()); break;
        }
        return true;
    }

    private void inicializar() {

        String email_summary = getString(R.string.pref_default_email_summary),
                company_summary = getString(R.string.pref_default_company_cloud_summary);

        try {
            // E-mail
            email = (EditTextPreference) findPreference(getString(R.string.pref_email_key));
            email_summary = email != null ? email.getText() : email_summary;
            if (email != null) {
                email.setSummary(email_summary);
                email.setOnPreferenceChangeListener(this);
            }
            // Company
            company = (EditTextPreference) findPreference(getString(R.string.pref_company_cloud_key));
            company_summary = company != null ? company.getText() : company_summary;
            if (company != null) {
                company.setSummary(company_summary);
                company.setOnPreferenceChangeListener(this);
            }
        } catch (NullPointerException e) {
            LogUtil.error(TAG, e.getMessage(), e);
        }

    }

}
