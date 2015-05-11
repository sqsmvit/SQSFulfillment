package com.sqsmv.sqsfulfillment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class DatabaseUpdateLauncher
{
    public static void startDBUpdate(final Context context)
    {
        final ProgressDialog pausingDialog = ProgressDialog.show(context, "Updating", "Please Stay in Wifi Range...", true);
        final DroidConfigManager appConfig = new DroidConfigManager(context);
        Intent popIntent = new Intent(context, PopDatabaseService.class);
        context.startService(popIntent);
        appConfig.accessBoolean(DroidConfigManager.UPDATE_LOCK, true, true);

        new Thread()
        {
            public void run()
            {
                while(appConfig.accessBoolean(DroidConfigManager.UPDATE_LOCK, null, true));
                pausingDialog.dismiss();
            }
        }.start();
    }
}
