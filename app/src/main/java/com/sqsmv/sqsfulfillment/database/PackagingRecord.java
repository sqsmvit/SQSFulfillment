package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

public class PackagingRecord implements DataRecord
{
    private String pkMasnum;
    private String packagingName;
    private String sha;

    public PackagingRecord()
    {
        pkMasnum = "";
        packagingName = "";
        sha = "";
    }

    public PackagingRecord(String masnum, String name, String shaVal)
    {
        pkMasnum = masnum;
        packagingName = name;
        sha = shaVal;
    }

    public String accessPkMasnum(String masnum)
    {
        if(masnum != null)
            pkMasnum = masnum;
        return pkMasnum;
    }

    public String accessPackagingName(String name)
    {
        if(name != null)
            packagingName = name;
        return packagingName;
    }

    public String accessSha(String shaVal)
    {
        if(shaVal != null)
            sha = shaVal;
        return sha;
    }

    @Override
    public DataRecord newCopy()
    {
        return new PackagingRecord(pkMasnum, packagingName, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case PackagingContract.COLUMN_NAME_PKMASNUM:
                accessPkMasnum(data);
                break;
            case PackagingContract.COLUMN_NAME_PACKAGINGNAME:
                accessPackagingName(data);
                break;
            case PackagingContract.COLUMN_NAME_SHA:
                accessSha(data);
                break;
        }
    }

    @Override
    public String getValueByKey(String key)
    {
        String value = "";

        switch(key)
        {
            case PackagingContract.COLUMN_NAME_PKMASNUM:
                value = accessPkMasnum(null);
                break;
            case PackagingContract.COLUMN_NAME_PACKAGINGNAME:
                value = accessPackagingName(null);
                break;
            case PackagingContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(PackagingContract.PACKAGING_COLUMNS);

        if(dbCursor.moveToFirst())
        {
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)) && !dbCursor.getColumnName(count).equals(XMLFileContract.COLUMN_NAME_SHA))
                    success = true;
                setValueByKey(dbCursor.getColumnName(count), dbCursor.getString(count));
            }
        }
        dbCursor.close();
        return success;
    }

    @Override
    public void clearRecord()
    {
        accessPkMasnum("");
        accessPackagingName("");
        accessSha("");
    }
}
