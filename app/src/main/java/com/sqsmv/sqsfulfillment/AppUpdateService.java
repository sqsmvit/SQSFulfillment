package com.sqsmv.sqsfulfillment;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.sqsmv.sqsfulfillment.database.DataAccess;
import com.sqsmv.sqsfulfillment.database.fulfillmentscan.FulfillmentScanDataAccess;
import com.sqsmv.sqsfulfillment.database.packresetscan.PackResetScanDataAccess;

import java.io.File;

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
        quickExportScan(fulfillmentScanDataAccess, ScanSource.FulfillmentScans);
        PackResetScanDataAccess packResetScanDataAccess = new PackResetScanDataAccess(this);
        quickExportScan(packResetScanDataAccess, ScanSource.ResetScans);

        downloadAPK();
        String dbxFileRev = dropboxManager.getDbxFileRev("/out/" + apkFileName);
        appConfig.accessString(DroidConfigManager.CURRENT_APK_REV, dbxFileRev, "");
        updateApp();
    }

    private void quickExportScan(DataAccess exportDataAccess, ScanSource exportScanSource)
    {
        exportDataAccess.open();
        Cursor fulfilledInvoicesCursor = exportDataAccess.selectAll();
        if(fulfilledInvoicesCursor.getCount() > 0)
        {
            while(!ScanWriter.exportFile(this, fulfilledInvoicesCursor, exportScanSource));
            exportDataAccess.deleteAll();
        }
        exportDataAccess.close();
    }

    private void downloadAPK()
    {
        dropboxManager.writeToStorage("/out/" + apkFileName, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + apkFileName, false);
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
