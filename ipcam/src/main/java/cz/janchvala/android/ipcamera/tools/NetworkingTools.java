package cz.janchvala.android.ipcamera.tools;

import com.google.common.collect.Lists;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by jan on 12.11.2014.
 */
public final class NetworkingTools {

    private NetworkingTools() {
    }

    public static ArrayList<String> getLocalIpAddress(int port) {
        ArrayList<String> ips = Lists.newArrayList();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    // for getting IPV4 format
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ips.add(String.format("rtsp://%s:%d", inetAddress.getHostAddress(), port));
                    }
                }
            }
        } catch (Exception ignore) {
        }
        return ips;
    }
}
