package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

public class ExportTrackingRecord implements DataRecord
{
    private String pkDate;
    private int numFulfillmentFiles;
    //private int numPackRecreateFiles;

    public ExportTrackingRecord()
    {
        pkDate = "";
        numFulfillmentFiles = 0;
        //numPackRecreateFiles = 0;
    }

    public ExportTrackingRecord(String date, int numFulfillmentExports)//, int numPackRecreateExports)
    {
        pkDate = date;
        numFulfillmentFiles = numFulfillmentExports;
        //numPackRecreateFiles = numPackRecreateExports;
    }

    public String accessPkDate(String date)
    {
        if(date != null)
        {
            pkDate = date;
        }
        return pkDate;
    }

    public int accessNumFulfillmentFiles(Integer numFulfillmentExports)
    {
        if(numFulfillmentExports != null)
        {
            numFulfillmentFiles = numFulfillmentExports.intValue();
        }
        return numFulfillmentFiles;
    }
/*
    public int accessNumPackRecreateFiles(Integer numPackRecreateExports)
    {
        if(numPackRecreateExports != null)
        {
            numPackRecreateFiles = numPackRecreateExports.intValue();
        }
        return numPackRecreateFiles;
    }
*/
    @Override
    public DataRecord newCopy()
    {
        return new ExportTrackingRecord(pkDate, numFulfillmentFiles);//, numPackRecreateFiles);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case ExportTrackingContract.COLUMN_NAME_PKDATE:
                accessPkDate(data);
                break;
            case ExportTrackingContract.COLUMN_NAME_NUMFULFILLMENTFILES:
                accessNumFulfillmentFiles(Integer.valueOf(data));
                break;
            /*
            case ExportTrackingContract.COLUMN_NAME_NUMPACKRECREATEFILES:
                accessNumPackRecreateFiles(Integer.valueOf(data));
                break;
            */
        }
    }

    @Override
    public String getValueByKey(String key)
    {
        String value = "";

        switch(key)
        {
            case ExportTrackingContract.COLUMN_NAME_PKDATE:
                value = accessPkDate(null);
                break;
            case ExportTrackingContract.COLUMN_NAME_NUMFULFILLMENTFILES:
                value = Integer.toString(accessNumFulfillmentFiles(null));
                break;
            /*
            case ExportTrackingContract.COLUMN_NAME_NUMPACKRECREATEFILES:
                value = Integer.toString(accessNumPackRecreateFiles(null));
                break;
            */
        }
        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(ExportTrackingContract.EXPORTTRACKING_COLUMNS);

        if(dbCursor.moveToFirst())
        {
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)))
                {
                    success = true;
                }
                setValueByKey(dbCursor.getColumnName(count), dbCursor.getString(count));
            }
        }
        dbCursor.close();
        return success;
    }

    @Override
    public void clearRecord()
    {
        accessPkDate("");
        accessNumFulfillmentFiles(0);
        //accessNumPackRecreateFiles(0);
    }
}
