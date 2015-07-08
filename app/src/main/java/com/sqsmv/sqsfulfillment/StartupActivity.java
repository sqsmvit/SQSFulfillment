//Checks for Dropbox connection and establishes one if there is not one

package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.cory.libraries.AppInfo;
import org.cory.libraries.DroidInfo;
import org.cory.libraries.FileHandling;
import org.cory.libraries.MoreDateFunctions;
import org.cory.libraries.QuickToast;

import java.io.File;

public class StartupActivity extends Activity
{
    private static final String TAG = "StartupActivity";

    DropboxManager dropboxManager;
    UpdateLauncher updateLauncher;

    //ToDo: Export tracking

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        dropboxManager = new DropboxManager(this);
        updateLauncher = new UpdateLauncher(this);

        Button launchScannerPairButton = (Button)findViewById(R.id.LaunchScannerPairButton);
        Button launchFulfillmentScanButton = (Button)findViewById(R.id.LaunchFulfillmentScanButton);

        TextView versionTextView = (TextView)findViewById(R.id.VersionText);
        versionTextView.setText("Version: " + AppInfo.getVersion(this));

        launchScannerPairButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchSMPairActivity();
            }
        });

        launchFulfillmentScanButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkDailyTasks();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(!dropboxManager.hasLinkedAccount())
        {
            linkDropboxAccount();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                dropboxManager.initDropboxFileSystem();
            }
            else
            {
                QuickToast.makeToast(this, "Error Connecting to Dropbox");
                finish();
            }
        }
    }

    private void launchSMPairActivity()
    {
        Intent intent = new Intent(this, SocketMobilePairActivity.class);
        startActivity(intent);
    }

    private void launchFulfillmentScanActivity()
    {
        Intent intent = new Intent(this, FulfillmentScanActivity.class);
        startActivity(intent);
    }

    //Checks WiFi and checks/links an account
    private void linkDropboxAccount()
    {
        if(DroidInfo.isWiFiConnected(this))
        {
            dropboxManager.linkDropboxAccount();
        }
        else
        {
            QuickToast.makeToast(this, "WiFi not connected");
            finish();
        }
    }

    private void checkDailyTasks()
    {
        DroidConfigManager appConfig = new DroidConfigManager(this);
        String today = MoreDateFunctions.getTodayYYMMDD();
        String lastUpdatedString = appConfig.accessString(DroidConfigManager.LAST_UPDATED, null, "");

        if(!today.equals(lastUpdatedString))
        {
            if(updateLauncher.checkNeedAppUpdate())
            {
                updateLauncher.startAppUpdate();
            }
            else
            {
                appConfig.accessString(DroidConfigManager.PRIOR_VERSION, "", "");
                appConfig.accessString(appConfig.LAST_UPDATED, today, "");
                FileHandling.cleanFolder(new File(Environment.getExternalStorageDirectory().toString() + "/FulfillBackups"), 180);
                launchDBUpdate();
            }
        }
        else
        {
            launchFulfillmentScanActivity();
        }
    }

    private void launchDBUpdate()
    {
        final Thread blockingThread = updateLauncher.startDBUpdate();
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    blockingThread.join();
                    launchFulfillmentScanActivity();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
