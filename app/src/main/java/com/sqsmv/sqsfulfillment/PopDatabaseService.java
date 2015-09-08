package com.sqsmv.sqsfulfillment;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.sqsmv.sqsfulfillment.database.config.ConfigDataAccess;
import com.sqsmv.sqsfulfillment.database.DataAccess;
import com.sqsmv.sqsfulfillment.database.invoice.InvoiceDataAccess;
import com.sqsmv.sqsfulfillment.database.lens.LensDataAccess;
import com.sqsmv.sqsfulfillment.database.pack.PackDataAccess;
import com.sqsmv.sqsfulfillment.database.packline.PackLineDataAccess;
import com.sqsmv.sqsfulfillment.database.packaging.PackagingDataAccess;
import com.sqsmv.sqsfulfillment.database.scanner.ScannerDataAccess;
import com.sqsmv.sqsfulfillment.database.shipto.ShipToDataAccess;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

import org.cory.libraries.FileHandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PopDatabaseService extends IntentService
{
	private static final String TAG = "PopDatabaseService";
    private final String zipFileName = "ffiles.zip";

	public PopDatabaseService()
    {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent)
    {
        DroidConfigManager appConfig = new DroidConfigManager(this);

		makeNotification("Dropbox Download Started", false);

        //Download files.zip from DropBox
        downloadDBXZip();
        File zipFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + zipFileName);
        try
        {
            FileHandling.unzip(zipFile);
            zipFile.delete();
            makeNotification("Database Update Started", false);
            resetTables();

            //UI will block for these, maybe
            XMLDataAccess[] necessaryDataAccesses = new XMLDataAccess[]{new InvoiceDataAccess(this), new PackDataAccess(this), new ConfigDataAccess(this), new ScannerDataAccess(this)};
            ArrayList<Thread> necessaryUpdateThreads = new ArrayList<>();
            for(XMLDataAccess xmlDataAccess : necessaryDataAccesses)
            {
                startUpdateThreads(xmlDataAccess, necessaryUpdateThreads);
            }
            joinUpdateThreads(necessaryUpdateThreads);
            appConfig.accessBoolean(DroidConfigManager.UPDATE_LOCK, false, true);

            XMLDataAccess[] otherDataAccesses = new XMLDataAccess[]{new ShipToDataAccess(this), new PackLineDataAccess(this), new PackagingDataAccess(this), new LensDataAccess(this)};
            ArrayList<Thread> otherUpdateThreads = new ArrayList<>();
            for(XMLDataAccess xmlDataAccess : otherDataAccesses)
            {
                startUpdateThreads(xmlDataAccess, otherUpdateThreads);
            }
            joinUpdateThreads(otherUpdateThreads);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        appConfig.accessBoolean(DroidConfigManager.UPDATE_LOCK, false, true);
        makeNotification("Database Update Finished", true);
	}

    private void downloadDBXZip()
    {
        DropboxManager dbxMan = new DropboxManager(this);

        dbxMan.writeToStorage("/out/" + zipFileName, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + zipFileName, false);
    }

    //Resets the tables that need to drop old information
    private void resetTables()
    {
        //Add new DataAccesses to array for resetting
        DataAccess[] resetingDataAccesses = new XMLDataAccess[]{new InvoiceDataAccess(this)};
        for(DataAccess dataAccess : resetingDataAccesses)
        {
            dataAccess.open();
            dataAccess.reset();
            dataAccess.close();
        }
    }

    private void startUpdateThreads(XMLDataAccess xmlDataAccess, ArrayList<Thread> updateThreadPool)
    {
        FMDumpHandler fmDumpHandler = new FMDumpHandler(xmlDataAccess);
        updateThreadPool.add(fmDumpHandler);
        fmDumpHandler.start();
    }

    private void joinUpdateThreads(ArrayList<Thread> updateThreads)
    {
        for(Thread updateThread : updateThreads)
        {
            try
            {
                updateThread.join();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

	private void makeNotification(String message, boolean finished)
	{
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setContentTitle("SQSFulfillment")
		        .setContentText(message)
		        .setTicker(message);
		long[] pattern = {0, 1000, 500, 1000};
		if(finished)
		{
	        mBuilder.setSmallIcon(R.drawable.ic_launcher);
			mBuilder.setVibrate(pattern);
		}
		else
        {
            mBuilder.setSmallIcon(R.drawable.ic_launcher);
        }
		// Creates an explicit intent for an Activity in your app
		Intent emptyIntent = new Intent();

		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT); 
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
	}
}
