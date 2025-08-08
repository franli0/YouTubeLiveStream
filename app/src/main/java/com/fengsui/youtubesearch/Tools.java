package com.fengsui.youtubesearch;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Tools {

    private static final String TAG = Tools.class.getSimpleName();

    public String ip;
    public String mac;

    public Tools() {
        getMacAddress();
    }

    private static InetAddress getLocalInetAddress()
    {
        InetAddress ip = null;
        try
        {
            // 列舉
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements())
            {
                // 是否還有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();
                // 得到下一個元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();
                // 得到一個 IP 位址的列舉
                while (en_ip.hasMoreElements())
                {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }
                if (ip != null)
                {
                    break;
                }
            }
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        return ip;
    }

    public void getMacAddress()
    {
        try
        {
            InetAddress ip = getLocalInetAddress();
            this.ip = ip.getHostAddress();
            // 獲得 IP 位址
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for(int i=0; i<b.length; i++)
            {
                if(i!=0)
                {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            this.mac = buffer.toString().toUpperCase();
        }
        catch (Exception e)
        {
            Log.d(TAG, "取得 MAC 失敗 == "+e.toString());
            this.mac ="02:00:00:00:00:00";
        }
    }
}
