package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

public class PackResetScanRecord implements DataRecord
{
    private String _id;
    private String fkPackId;
    private String fkPullMasterId;
    private String quantity;
    private String scanId;
    private String scanDate;
    private String scannerInitials;
    private String scannedPackName;

    public PackResetScanRecord()
    {
        _id = "";
        fkPackId = "";
        quantity = "";
        scanId = "";
        scanDate = "";
        scannerInitials = "";
        scannedPackName = "";
    }

    public PackResetScanRecord(String packId, String pullMasterId, String packQuantity, String scanNum, String date, String initials, String packName)
    {
        _id = "null";
        fkPackId = packId;
        fkPullMasterId = pullMasterId;
        quantity = packQuantity;
        scanId = scanNum;
        scanDate = date;
        scannerInitials = initials;
        scannedPackName = packName;
    }

    public PackResetScanRecord(String recId, String packId, String pullMasterId, String packQuantity, String scanNum, String date, String initials, String packName)
    {
        _id = recId;
        fkPackId = packId;
        fkPullMasterId = pullMasterId;
        quantity = packQuantity;
        scanId = scanNum;
        scanDate = date;
        scannerInitials = initials;
        scannedPackName = packName;
    }

    public String access_Id(String recId)
    {
        if(recId != null)
        {
            _id = recId;
        }
        return _id;
    }

    public String accessFkPackId(String packId)
    {
        if(packId != null)
        {
            fkPackId = packId;
        }
        return fkPackId;
    }

    public String accessFkPullMasterId(String pullMasterId)
    {
        if(pullMasterId != null)
        {
            fkPullMasterId = pullMasterId;
        }
        return fkPullMasterId;
    }

    public String accessQuantity(String packQuantity)
    {
        if(packQuantity != null)
        {
            quantity = packQuantity;
        }
        return quantity;
    }

    public String accessScanId(String scanNum)
    {
        if(scanNum != null)
        {
            scanId = scanNum;
        }
        return scanId;
    }

    public String accessScanDate(String date)
    {
        if(date != null)
        {
            scanDate = date;
        }
        return scanDate;
    }

    public String accessScannerInitials(String initials)
    {
        if(initials != null)
        {
            scannerInitials = initials;
        }
        return scannerInitials;
    }

    public String accessScannedPackName(String packName)
    {
        if(packName != null)
        {
            scannedPackName = packName;
        }
        return scannedPackName;
    }

    @Override
    public DataRecord newCopy()
    {
        return new PackResetScanRecord(_id, fkPackId, fkPullMasterId, quantity, scanId, scanDate, scannerInitials, scannedPackName);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case PackResetScanContract._ID:
                access_Id(data);
                break;
            case PackResetScanContract.COLUMN_NAME_FKPACKID:
                accessFkPackId(data);
                break;
            case PackResetScanContract.COLUMN_NAME_FKPULLMASTERID:
                accessFkPullMasterId(data);
                break;
            case PackResetScanContract.COLUMN_NAME_QUANTITY:
                accessQuantity(data);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANID:
                accessScanId(data);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANDATE:
                accessScanDate(data);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANNERINITIALS:
                accessScannerInitials(data);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANNEDPACKNAME:
                accessScannedPackName(data);
                break;
        }
    }

    @Override
    public String getValueByKey(String key)
    {
        String value = "";

        switch(key)
        {
            case PackResetScanContract._ID:
                value = access_Id(null);
                break;
            case PackResetScanContract.COLUMN_NAME_FKPACKID:
                value = accessFkPackId(null);
                break;
            case PackResetScanContract.COLUMN_NAME_FKPULLMASTERID:
                value = accessFkPullMasterId(null);
                break;
            case PackResetScanContract.COLUMN_NAME_QUANTITY:
                value = accessQuantity(null);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANID:
                value = accessScanId(null);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANDATE:
                value = accessScanDate(null);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANNERINITIALS:
                value = accessScannerInitials(null);
                break;
            case PackResetScanContract.COLUMN_NAME_SCANNEDPACKNAME:
                value = accessScannedPackName(null);
                break;
        }
        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(PackResetScanContract.PACKRESETSCAN_COLUMNS);

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
        access_Id("");
        accessFkPackId("");
        accessFkPullMasterId("");
        accessQuantity("");
        accessScanId("");
        accessScanDate("");
        accessScannerInitials("");
        accessScannedPackName("");
    }
}
