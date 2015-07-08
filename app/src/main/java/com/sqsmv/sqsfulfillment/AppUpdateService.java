package com.sqsmv.sqsfulfillment;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.dropbox.sync.android.DbxFileInfo;
import com.sqsmv.sqsfulfillment.database.FulfillmentScanDataAccess;

import org.cory.libraries.MoreDateFunctions;

import java.io.File;
import java.util.Date;

public class AppUpdateService extends IntentService
{
    private static final String TAG = "AppUpdateService";
    private String apkFileName;
    private DropboxManager dropboxManager;

    public AppUpdateService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        apkFileName = getString(R.string.apk_file_name);
        dropboxManager = new DropboxManager(this);

        DroidConfigManager appConfig = new DroidConfigManager(this);

        FulfillmentScanDataAccess fulfillmentScanDataAccess = new FulfillmentScanDataAccess(this);
        fulfillmentScanDataAccess.open();
        Cursor fulfilledInvoicesCursor = fulfillmentScanDataAccess.selectAll();
        if(fulfilledInvoicesCursor.getCount() > 0)
        {
            while(!ScanWriter.exportFile(this, fulfilledInvoicesCursor));
            fulfillmentScanDataAccess.deleteAll();
        }
        fulfillmentScanDataAccess.close();

        downloadAPK();
        DbxFileInfo dbxFileInfo = dropboxManager.getDbxFileInfo("/out/" + apkFileName);
        Date fileModifiedDate = dbxFileInfo.modifiedTime;
        appConfig.accessString(DroidConfigManager.LAST_APP_UPDATE, MoreDateFunctions.formatDateAsFileTimestamp(fileModifiedDate), "");
        updateApp();
    }

    private void downloadAPK()
    {
        //Download update.txt to update DropBox to know about latest version of zip file
        dropboxManager.writeToStorage("/out/update.txt", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + "update.txt");
        File updateFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + "update.txt");
        updateFile.delete();
        dropboxManager.writeToStorage("/out/" + apkFileName, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + apkFileName);
    }

    private void updateApp()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + apkFileName)),
                              "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
        startActivity(intent);
    }
}
