package com.sqsmv.sqsfulfillment;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import org.cory.libraries.DroidInfo;
import org.cory.libraries.FileHandling;
import org.cory.libraries.MoreDateFunctions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScanWriter
{
    public static boolean exportFile(Context context, Cursor scanCursor, ScanSource scanSource)
    {
        boolean success = false;
        String directory = "";
        switch(scanSource)
        {
            case FulfillmentScans:
                directory = "fulfillments";
            case ResetScans:
                directory = "reset";
        }

        try
        {
            File exportFile = ScanWriter.writeExportFile(context, scanCursor);
            DropboxManager dropboxManager = new DropboxManager(context);
            dropboxManager.writeToDropbox(exportFile, File.separator + directory + File.separator + exportFile.getName(), true);
            success = true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return success;
    }

    private static File writeExportFile(Context context, Cursor scanCursor) throws IOException
    {
        String fileName = DroidInfo.getDeviceName() + "_" + MoreDateFunctions.getNowFileTimestamp() + ".txt";
        File exportFile = new File(context.getFilesDir() + File.separator + fileName);
        writeToFile(exportFile, scanCursor);
        writeBackup(exportFile);

        return exportFile;
    }

    private static void writeToFile(File writeFile, Cursor scanCursor) throws IOException
    {
        String writeString;
        FileOutputStream output = new FileOutputStream(writeFile, true);

        while (scanCursor.moveToNext())
        {
            writeString = buildString(scanCursor);
            output.write(writeString.getBytes());
        }

        output.close();
        scanCursor.close();
    }

    private static void writeBackup(File copyFile) throws IOException
    {
        File root = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "FulfillmentBackups");
        root.mkdirs();
        File backupFile = new File(root.getAbsolutePath(), "B_" + copyFile.getName());
        FileHandling.copyFile(copyFile, backupFile);
    }

    private static String buildString(Cursor scanCursor)
    {
        String writeString = "";

        int columnCount = scanCursor.getColumnCount();

        //Skip pKey
        for(int count = 1; count < columnCount; count++)
        {
            if(count == columnCount - 1)
            {
                writeString += scanCursor.getString(count) + "\n";
            }
            else
            {
                writeString += scanCursor.getString(count) + "\t";
            }
        }

        return writeString;
    }
}
