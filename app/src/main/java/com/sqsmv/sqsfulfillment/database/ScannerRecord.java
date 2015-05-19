package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

public class ScannerRecord implements DataRecord
{
    private String pkScannerId;
    private String fullName;
    private String isManagement;
    private String isTemp;
    private String sha;

    public ScannerRecord()
    {
        pkScannerId = "";
        fullName = "";
        isManagement = "";
        isTemp = "";
        sha = "";
    }

    public ScannerRecord(String scannerId, String name, String isManager, String isTempEmployee, String shaVal)
    {
        pkScannerId = scannerId;
        fullName = name;
        isManagement = isManager;
        isTemp = isTempEmployee;
        sha = shaVal;
    }

    public String accessPkScannerId(String scannerId)
    {
        if(scannerId != null)
            pkScannerId = scannerId;
        return pkScannerId;
    }

    public String accessFullName(String name)
    {
        if(name != null)
            fullName = name;
        return fullName;
    }

    public String accessIsManagement(String isManager)
    {
        if(isManager != null)
            isManagement = isManager;
        return isManagement;
    }

    public String accessIsTemp(String isTempEployee)
    {
        if(isTempEployee != null)
            isTemp = isTempEployee;
        return isTemp;
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
        return new ScannerRecord(pkScannerId, fullName, isManagement, isTemp, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case ScannerContract.COLUMN_NAME_PKSCANNERID:
                accessPkScannerId(data);
                break;
            case ScannerContract.COLUMN_NAME_FULLNAME:
                accessFullName(data);
                break;
            case ScannerContract.COLUMN_NAME_ISMANAGEMENT:
                accessIsManagement(data);
                break;
            case ScannerContract.COLUMN_NAME_ISTEMP:
                accessIsTemp(data);
                break;
            case ScannerContract.COLUMN_NAME_SHA:
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
            case ScannerContract.COLUMN_NAME_PKSCANNERID:
                value = accessPkScannerId(null);
                break;
            case ScannerContract.COLUMN_NAME_FULLNAME:
                value = accessFullName(null);
                break;
            case ScannerContract.COLUMN_NAME_ISMANAGEMENT:
                value = accessIsManagement(null);
                break;
            case ScannerContract.COLUMN_NAME_ISTEMP:
                value = accessIsTemp(null);
                break;
            case ScannerContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(ScannerContract.SCANNER_COLUMNS);

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
        accessPkScannerId("");
        accessFullName("");
        accessIsManagement("");
        accessIsTemp("");
        accessSha("");
    }
}
