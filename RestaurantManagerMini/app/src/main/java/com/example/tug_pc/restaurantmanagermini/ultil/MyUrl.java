package com.example.tug_pc.restaurantmanagermini.ultil;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Enumeration;

import static android.content.Context.WIFI_SERVICE;

@SuppressWarnings("ALL")
public class MyUrl {

    public static String localhost = "192.168.1.2";

    public static String getUrlData = "http://" + localhost + "/server/";

    private static String getLocalIpAddressV4() throws UnknownHostException {
        // Returns the instance of InetAddress containing
        // local host name and address
        InetAddress localhost = InetAddress.getLocalHost();
        Log.e("System IP Address : ", localhost.getHostAddress().trim());

        // Find public IP address
        String systemipaddress = "";
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            systemipaddress = sc.readLine().trim();
        }
        catch (Exception e)
        {
            systemipaddress = "Cannot Execute Properly";
        }
        Log.e("Public IP Address: ", systemipaddress +"\n");

        return systemipaddress;
    }

    public static String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString = null;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return "http://" + ipAddressString + "/server/";
    }
}
