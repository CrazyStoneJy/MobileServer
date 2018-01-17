package me.crazystone.study.mobileserver;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by crazystone on 18-1-17.
 */

public class Wifis {

    public static String getIp(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager == null)
            return "";
        if (!manager.isWifiEnabled()) {
            manager.setWifiEnabled(true);
        }
        int ip = manager.getConnectionInfo().getIpAddress();
        return format_ip(ip);
    }

    public static String format_ip(int ip) {
        StringBuilder host = new StringBuilder();
        host.append(ip & 0xff).append(".")
                .append(ip >>> 8 & 0xff).append(".")
                .append(ip >>> 16 & 0xff).append(".")
                .append(ip >>> 24 & 0xff);
        return host.toString();
    }


}
