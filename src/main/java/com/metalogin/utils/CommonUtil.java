package com.metalogin.utils;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

/**
 * created by yumira 2018/7/24.
 */
public class CommonUtil {

    public static Long getNow() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        InetAddress inetAddress = getLocalHostLANAddress();
        System.out.println(inetAddress.getHostName());
    }

    public static String getLocalIp() {
        InetAddress inetAddress = getLocalHostLANAddress();
        if (inetAddress != null) {
            return inetAddress.getHostAddress();
        }
        return null;
    }

    /**
     * 生成uid
     * @return
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    //根据指定长度生成纯数字的随机数
    public static long generateNonce() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for(int i = 0;i < 16;i++)
        {
            sb.append(rand.nextInt(10));
        }
        return Long.parseLong(sb.toString());
    }

    /**
     * 获取本机ip地址
     */
    public static InetAddress getLocalHostLANAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
