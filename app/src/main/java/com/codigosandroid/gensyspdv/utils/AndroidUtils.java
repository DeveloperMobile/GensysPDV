package com.codigosandroid.gensyspdv.utils;


import android.content.Context;
import android.content.res.Configuration;

import com.codigosandroid.utils.utils.AndroidUtil;
import com.codigosandroid.utils.utils.LogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

/**
 * Created by Tiago on 28/12/2017.
 */

public class AndroidUtils extends AndroidUtil {

    private static final String TAG = AndroidUtils.class.getSimpleName();

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

    public static boolean localizaHost(String host, int port) {
        SocketAddress sockaddr = new InetSocketAddress(host, port);
        Socket socket = new Socket();
        boolean online = true;

        try {
            socket.connect(sockaddr, 10000);
        } catch (SocketTimeoutException e) {
            online = false;
            LogUtil.error(TAG, "SocketTimeoutException: " + e.getMessage());
        } catch (IOException e) {
            online = false;
            LogUtil.error(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LogUtil.error(TAG, "IOException: " + e.getMessage());
            }

        }

        return online;

    }

}
