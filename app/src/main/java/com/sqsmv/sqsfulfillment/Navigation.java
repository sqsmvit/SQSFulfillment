package com.sqsmv.sqsfulfillment;

import android.content.Context;
import android.content.Intent;

public class Navigation
{
    public static void launchAdminActivity(Context activityContext)
    {
        activityContext.startActivity(new Intent(activityContext, AdminActivity.class));
    }

    public static void launchPackResetScanActivity(Context activityContext)
    {
        activityContext.startActivity(new Intent(activityContext, PackResetScanActivity.class));
    }

    public static void launchSMPairActivity(Context activityContext)
    {
        activityContext.startActivity(new Intent(activityContext, SocketMobilePairActivity.class));
    }
}
