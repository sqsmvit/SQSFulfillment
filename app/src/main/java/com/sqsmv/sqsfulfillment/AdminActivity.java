package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sqsmv.sqsfulfillment.database.DBAdapter;

import org.cory.libraries.MoreDateFunctions;
import org.cory.libraries.QuickToast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class AdminActivity extends Activity
{
    UpdateLauncher updateLauncher;
    ArrayList<HashMap<String, String>> backupList;
    File backupDir;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        updateLauncher = new UpdateLauncher(this);
        backupDir = new File(Environment.getExternalStorageDirectory().toString() + "/FulfillmentBackups");

        Button adminBackButton = (Button)findViewById(R.id.AdminBackButton);

        Button forceAppUpdateButton = (Button)findViewById(R.id.AppUpdateButton);
        Button forceDBUpdateButton = (Button)findViewById(R.id.ForceUpdateButton);
        Button ResetDBButton = (Button)findViewById(R.id.ResetDBButton);
        ListView backupFileListView = (ListView)findViewById(R.id.BackupFileList);

        backupDir.mkdir();

        backupList = createAdapterDataset(backupDir);

        SimpleAdapter backupAdapter = new SimpleAdapter(this, backupList, R.layout.row_file_info,
                                          new String[] {"fileName","fileDate","fileSize"},
                                          new int[]{R.id.FileName, R.id.FileDate, R.id.FileSize});

        backupFileListView.setAdapter(backupAdapter);

        adminBackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        forceAppUpdateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forceAppUpdate();
            }
        });

        forceDBUpdateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forceDBUpdate();
            }
        });

        ResetDBButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetDB();
            }
        });

        backupFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                File selectedFile = new File(backupDir, backupList.get(position).get("fileName"));
                exportBackupPrompt(selectedFile);
            }
        });
    }

    private ArrayList<HashMap<String, String>> createAdapterDataset(File directory)
    {
        ArrayList<HashMap<String, String>> fileInfoList = new ArrayList<>();
        HashMap<String, String> backupEntry;
        File[] files = directory.listFiles();

        for(File file:files)
        {
            backupEntry = new HashMap<>();
            backupEntry.put("fileName", file.getName());
            backupEntry.put("fileDate", MoreDateFunctions.formatDateAsSlashMMDDYY(new Date(file.lastModified())));
            backupEntry.put("fileSize", String.format("%.2f",(file.length()/1024.0)) + "kb");
            fileInfoList.add(backupEntry);
        }

        return fileInfoList;
    }

    private void forceAppUpdate()
    {
        if(updateLauncher.checkNeedAppUpdate())
        {
            updateLauncher.startAppUpdate();
        }
        else
        {
            QuickToast.makeToast(this, "This app is already at the latest version.");
        }
    }

    private void forceDBUpdate()
    {
        updateLauncher.startDBUpdate();
    }

    //Resets the tables that need to drop old information
    private void resetDB()
    {
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.resetAll();
    }

    private void exportBackupPrompt(final File exportFile)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Export Backup");

        // set dialog message
        alertDialogBuilder
                .setMessage("Send Backup to Dropbox?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        DropboxManager dropboxManager = new DropboxManager(getApplicationContext());
                        dropboxManager.writeToDropbox(exportFile, File.separator + "default" + File.separator + exportFile.getName());
                    }
                })
                .setNegativeButton("No", null);
        // create alert dialog
        AlertDialog exportBackupDialog = alertDialogBuilder.create();
        exportBackupDialog.show();
    }
}
