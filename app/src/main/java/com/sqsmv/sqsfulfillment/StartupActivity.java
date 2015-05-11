//Checks for Dropbox connection and establishes one if there is not one

package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import org.cory.libraries.DroidInfo;
import org.cory.libraries.FileHandling;
import org.cory.libraries.MoreDateFunctions;
import org.cory.libraries.QuickToast;

import java.io.File;

public class StartupActivity extends Activity
{
    private static final String TAG = "StartupActivity";

    DropboxManager dropboxManager;

    //ToDo: Export tracking

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        dropboxManager = new DropboxManager(this);


        Button launchScannerPairButton = (Button)findViewById(R.id.LaunchScannerPairButton);
        Button launchFulfillmentScanButton = (Button)findViewById(R.id.LaunchFulfillmentScanButton);

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
                launchFulfillmentScanActivity();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(dropboxManager.hasLinkedAccount())
        {
            checkNeedUpdateDatabase();
        }
        else
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
                checkNeedUpdateDatabase();
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

    private void checkNeedUpdateDatabase()
    {
        String today = MoreDateFunctions.getTodayYYMMDD();
        DroidConfigManager appConfig = new DroidConfigManager(this);
        String lastUpdatedString = appConfig.accessString(appConfig.LAST_UPDATED, null, "");

        if(!today.equals(lastUpdatedString))
        {
            //Do update stuffs
            appConfig.accessString(appConfig.LAST_UPDATED, today, "");
            DatabaseUpdateLauncher.startDBUpdate(this);
            FileHandling.cleanFolder(new File(Environment.getExternalStorageDirectory().toString() + "/FulfillBackups"), 180);
        }
    }
}
