package com.aguillen.supermarketshoppingmobile.util;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;

public class Environment {

    //private static final String ENVIRONMENT = "PRD";
    private static final String ENVIRONMENT = "DEV";
    private static final String HOST_PRD = "https://supermarketshopping.herokuapp.com";
    private static final String PORT = "8080";

    private static String getIp() throws Exception {
        String ip = null;
        try {
            InetAddress inetAddress = Inet4Address.getLocalHost();
            System.out.println("IP Address:- " + inetAddress.getHostAddress());
            System.out.println("Host Name:- " + inetAddress.getHostName());
            ip = inetAddress.getHostAddress();
            return ip;
        } catch (Exception ex) {
            throw new Exception("Se produjo un error al obtener el host");
        }
    }

    public static String getHost() throws Exception {
        try {
            if (ENVIRONMENT.equals("PRD")) {
                return HOST_PRD;
            } else if (ENVIRONMENT.equals("DEV")) {
                return "http://" + getIp() + ":" + PORT;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }
}
