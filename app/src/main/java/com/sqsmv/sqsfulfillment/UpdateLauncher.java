package com.sqsmv.sqsfulfillment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.dropbox.sync.android.DbxFileInfo;

import org.cory.libraries.AppInfo;
import org.cory.libraries.MoreDateFunctions;

import java.util.Date;

public class UpdateLauncher
{
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
        appConfig.accessBoolean(DroidConfigManager.UPDATE_LOCK, true, true);

        Thread pausingDialogThread = new Thread()
        {
            @Override
            public void run()
            {
                while(appConfig.accessBoolean(DroidConfigManager.UPDATE_LOCK, null, true));
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
        String lastUpdated = appConfig.accessString(DroidConfigManager.LAST_APP_UPDATE, null, "");
        dropboxManager.getDbxFileInfo("/out/" + apkFileName); //Accounting for outdated dropbox file info
        DbxFileInfo dbxFileInfo = dropboxManager.getDbxFileInfo("/out/" + apkFileName);
        Date fileModifiedDate = dbxFileInfo.modifiedTime;
        String fileModifiedDateString = MoreDateFunctions.formatDateAsFileTimestamp(fileModifiedDate);

        if(lastUpdated.isEmpty())
        {
            appConfig.accessString(DroidConfigManager.LAST_APP_UPDATE, fileModifiedDateString, "");
        }
        else if(!lastUpdated.equals(fileModifiedDateString) || appConfig.accessString(DroidConfigManager.PRIOR_VERSION, null, "").equals(AppInfo.getVersion(context)))
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
}