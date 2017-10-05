//Checks for Dropbox connection and establishes one if there is not one

package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dropbox.core.android.Auth;

import org.cory.libraries.AppInfo;
import org.cory.libraries.DroidInfo;
import org.cory.libraries.FileHandling;
import org.cory.libraries.MoreDateFunctions;
import org.cory.libraries.QuickToast;

import java.io.File;

public class StartupActivity extends Activity
{
    private static final String TAG = "StartupActivity";

    private DroidConfigManager appConfig;
    private DropboxManager dropboxManager;
    private UpdateLauncher updateLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        appConfig = new DroidConfigManager(this);
        dropboxManager = new DropboxManager(this);
        updateLauncher = new UpdateLauncher(this);

        Button launchFulfillmentScanButton = (Button)findViewById(R.id.LaunchFulfillmentScanButton);

        TextView versionTextView = (TextView)findViewById(R.id.VersionText);
        versionTextView.setText("Version: " + AppInfo.getVersion(this));

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

        String accessToken = appConfig.accessString(DroidConfigManager.DROPBOX_ACCESS_TOKEN, null, null);
        if (accessToken == null)
        {
            accessToken = Auth.getOAuth2Token();
            if (accessToken != null)
            {
                appConfig.accessString(DroidConfigManager.DROPBOX_ACCESS_TOKEN, accessToken, null);
                dropboxManager.initDbxClient(accessToken);
            }
            else
            {
                if(DroidInfo.isWiFiConnected(this))
                {
                    dropboxManager.linkDropboxAccount();
                }
                else
                {
                    QuickToast.makeLongToast(this, "Must be connected to WiFi to link to Dropbox!");
                    finish();
                }
            }
        }
        else
        {
            dropboxManager.initDbxClient(accessToken);
        }
    }

    private void launchPackResetScanActivity()
    {
        Intent intent = new Intent(this, PackResetScanActivity.class);
        startActivity(intent);
    }

    private void launchFulfillmentScanActivity()
    {
        Intent intent = new Intent(this, FulfillmentScanActivity.class);
        startActivity(intent);
    }

    private void checkDailyTasks()
    {
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
            //launchFulfillmentScanActivity();
            launchPackResetScanActivity();
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
                    //launchFulfillmentScanActivity();
                    launchPackResetScanActivity();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
