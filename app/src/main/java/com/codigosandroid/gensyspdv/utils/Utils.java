package com.codigosandroid.gensyspdv.utils;


import android.app.ActivityManager;
import android.content.Context;

import com.codigosandroid.utils.utils.LogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tiago on 28/12/2017.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static DecimalFormat formato = new DecimalFormat();

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

    public static boolean isServiceRunning(Context context, String serviceClassName) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        LogUtil.debug(TAG, "Procurando Serviçoes");
        for (int i = 0; i < services.size(); i++) {
            LogUtil.debug(TAG, "Service Nr. " + i + " class name: " + services.get(i).service.getClassName());
            if (services.get(i).service.getClassName().compareTo(serviceClassName) == 0) {
                return true;
            }
        }

        return false;

    }

    /* Retorna data atual */
    public static String getDataAtual() {

        Calendar calendar = Calendar.getInstance();
        Date data = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
        LogUtil.debug(TAG, sdf.format(data));
        return sdf.format(data);

    }

    /* Gera documento venda(VN) */
    public static String getDocumento(long id) {

        formato.applyPattern("00000000");
        return formato.format(id);

    }

}
