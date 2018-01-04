package com.codigosandroid.gensyspdv.utils;


import com.codigosandroid.utils.utils.LogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

/**
 * Created by Tiago on 28/12/2017.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

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
