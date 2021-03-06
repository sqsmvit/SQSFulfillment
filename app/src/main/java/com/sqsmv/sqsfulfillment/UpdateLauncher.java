package com.sqsmv.sqsfulfillment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import org.cory.libraries.AppInfo;

import java.util.concurrent.Semaphore;

public class UpdateLauncher
{
    private static Semaphore updateLock = new Semaphore(0);
    private Context context;
    private DroidConfigManager appConfig;
    private DropboxManager dropboxManager;

    public UpdateLauncher(Context activityContext)
    {
        context = activityContext;
        appConfig = new DroidConfigManager(activityContext);
        dropboxManager = new DropboxManager(context);
    }

    public Thread startDBUpdate()
    {
        final ProgressDialog pausingDialog = ProgressDialog.show(context, "Updating Database", "Please Stay in Wifi Range...", true);
        Intent popIntent = new Intent(context, PopDatabaseService.class);
        context.startService(popIntent);

        Thread pausingDialogThread = new Thread()
        {
            @Override
            public void run()
            {
                acquireUpdateLock();
                pausingDialog.dismiss();
            }
        };
        pausingDialogThread.start();
        return pausingDialogThread;
    }

    public boolean checkNeedAppUpdate()
    {
        boolean needUpdate = false;

        String apkFileName = context.getString(R.string.apk_file_name);
        String currentRev = appConfig.accessString(DroidConfigManager.CURRENT_APK_REV, null, "");
        String dbxFileRev = dropboxManager.getDbxFileRev("/out/" + apkFileName);

        if(currentRev.isEmpty())
        {
            appConfig.accessString(DroidConfigManager.CURRENT_APK_REV, dbxFileRev, "");
        }
        else if(!currentRev.equals(dbxFileRev) || appConfig.accessString(DroidConfigManager.PRIOR_VERSION, null, "").equals(AppInfo.getVersion(context)))
        {
            needUpdate = true;
        }

        return needUpdate;
    }

    public void startAppUpdate()
    {
        appConfig.accessString(DroidConfigManager.PRIOR_VERSION, AppInfo.getVersion(context), "");
        ProgressDialog.show(context, "Updating Application", "Scans have been committed. Please Stay in Wifi Range...", true);
        Intent appUpdateIntent = new Intent(context, AppUpdateService.class);
        context.startService(appUpdateIntent);
    }

    public static void acquireUpdateLock()
    {
        try
        {
            updateLock.acquire();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void releaseUpdateLock()
    {
        updateLock.release();
    }
}
