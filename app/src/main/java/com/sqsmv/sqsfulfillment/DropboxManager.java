package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

import org.cory.libraries.QuickToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DropboxManager
{
    private static final String TAG = "CheckDropboxActivity";

    private Context context;
    private DbxAccountManager dbxAccountManager;
    private DbxFileSystem dbxFileSystem;

    DropboxManager(Context activityContext)
    {
        context = activityContext;

        String dropboxAppKey = context.getString(R.string.DBX_APP_KEY);
        String dropboxAppSecret = context.getString(R.string.DBX_SECRET_KEY);
        dbxAccountManager = DbxAccountManager.getInstance(activityContext.getApplicationContext(), dropboxAppKey, dropboxAppSecret);

        if(hasLinkedAccount())
        {
            initDropboxFileSystem();
            clearCache();
        }
    }

    public boolean linkDropboxAccount()
    {
        boolean accountLinked = false;
        if(!hasLinkedAccount())
        {
            dbxAccountManager.startLink((Activity)context, 1);
            accountLinked = true;
        }
        return accountLinked;
    }

    public boolean hasLinkedAccount()
    {
        return dbxAccountManager.hasLinkedAccount();
    }

    public void initDropboxFileSystem()
    {
        try
        {
            dbxFileSystem = DbxFileSystem.forAccount(dbxAccountManager.getLinkedAccount());
        }
        catch (DbxException.Unauthorized e)
        {
            e.printStackTrace();
        }
    }

    public void writeToStorage(String dbxFilePath, String downloadPath)
    {
        Log.d(TAG, "writeToStorage begin");
        DbxPath dropboxPath = new DbxPath(dbxFilePath);
        try
        {
            DbxFile inFile = dbxFileSystem.open(dropboxPath);
            inFile.getNewerStatus();
            try
            {
                FileOutputStream outStream = new FileOutputStream(downloadPath);

                byte[] buff = new byte[1024];
                FileInputStream inStream = inFile.getReadStream();
                int len;

                while((len = inStream.read(buff)) > 0)
                {
                    outStream.write(buff, 0, len);
                }
                outStream.close();
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
                Log.e(TAG, "writeToStorage: FileNotFoundException");
            }
            catch(IOException e)
            {
                e.printStackTrace();
                Log.e(TAG, "writeToStorage: IOException");
            }
            inFile.close();
            Log.d(TAG, "writeToStorage end");
        }
        catch(DbxException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToDropbox(File fileToWrite, String dbxFilePath, boolean shouldSteal)
    {
        DbxPath outPath = new DbxPath(dbxFilePath);
        try
        {
            DbxFile outFile = dbxFileSystem.create(outPath);
            outFile.getNewerStatus();

            try
            {
                outFile.writeFromExistingFile(fileToWrite, shouldSteal);
            }
            catch(IOException e)
            {
                e.printStackTrace();
                Log.e(TAG, "writeToDropbox: IOException");
            }
            QuickToast.makeToast(context, "File exported to DropBox");
            outFile.close();
        }
        catch(DbxException e)
        {
            e.printStackTrace();
        }
    }

    private void clearCache()
    {
        try
        {
            dbxFileSystem.setMaxFileCacheSize(0);
        }
        catch(DbxException e)
        {
            e.printStackTrace();
        }
    }
}
