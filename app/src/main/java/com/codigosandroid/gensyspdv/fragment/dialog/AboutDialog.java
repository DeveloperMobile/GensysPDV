package com.codigosandroid.gensyspdv.fragment.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.codigosandroid.gensyspdv.R;
import com.codigosandroid.gensyspdv.utils.AndroidUtils;
import com.codigosandroid.utils.utils.AndroidUtil;

/**
 * Created by Tiago on 12/09/2017.
 */

public class AboutDialog extends DialogFragment {

    /* Método utilitário para mostrar o dialog */
    public static void showAbout(FragmentManager fm) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");

        if (prev != null) {

            ft.remove(prev);

        }

        ft.addToBackStack(null);
        new AboutDialog().show(ft, "dialog_about");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Cria o HTML com o texto de about
        SpannableStringBuilder aboutBody = new SpannableStringBuilder();
        String versionName = AndroidUtil.getVersionName(getActivity());
        String serial = AndroidUtils.getSerial(getActivity());

        if (AndroidUtil.isAndroid7Nougat()) {

            aboutBody.append(Html.fromHtml(getString(R.string.about_dialog_text, versionName, serial), Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL));

        } else {

            aboutBody.append(Html.fromHtml(getString(R.string.about_dialog_text, versionName, serial)));

        }

        // Infla o layoutz
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TextView view = (TextView) inflater.inflate(R.layout.dialog_about, null);
        view.setText(aboutBody);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        // Cria o dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();

    }

}
