//Library of functions to find Droid device status and info

package org.cory.libraries;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class DroidInfo
{
    public static boolean isWiFiConnected(Context callingContext)
    {
        ConnectivityManager connManager = (ConnectivityManager)callingContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifi.isConnected();
    }

    public static String getDeviceName()
    {
        return BluetoothAdapter.getDefaultAdapter().getName();
    }
}
