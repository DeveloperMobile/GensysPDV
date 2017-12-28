package com.codigosandroid.gensyspdv.utils;


import android.content.Context;
import android.content.res.Configuration;

import com.codigosandroid.utils.utils.AndroidUtil;

/**
 * Created by Tiago on 28/12/2017.
 */

public class AndroidUtils extends AndroidUtil {

    /* Verifica se o dispositivo possui imei, se não utiliza o android_id */
    public static String getSerial(Context context) {

        String serial = null;

        if (AndroidUtil.getImei(context) == null) {

            serial = AndroidUtil.getAndroidId(context);

        } else {

            serial = AndroidUtil.getImei(context);

        }

        return serial;

    }

    // Retorna se a tela é large ou xlarge
    public static boolean isTablet(Context context) {

        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;

    }

}
