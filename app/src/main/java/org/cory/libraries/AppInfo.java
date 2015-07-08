package org.cory.libraries;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppInfo
{
    public static String getVersion(Context callingContext)
    {
        String version = "";

        try
        {
            PackageInfo packageInfo = callingContext.getPackageManager().getPackageInfo(callingContext.getPackageName(), 0);
            version = packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        return version;
    }
}
