package com.sqsmv.sqsfulfillment.database.unimplemented;

import android.database.Cursor;

import com.sqsmv.sqsfulfillment.database.DataRecord;

import java.util.Arrays;
import java.util.List;

public class PackRecreateScanRecord implements DataRecord
{
    private String _id;
    private String fkPackId;
    private String operation;
    private String scanDate;
    private String scannerInitials;
    private String scannedPackName;

    public PackRecreateScanRecord()
    {
        _id = "";
        fkPackId = "";
        operation = "";
        scanDate = "";
        scannerInitials = "";
        scannedPackName = "";
    }

    public PackRecreateScanRecord(String packId, String operationType, String date, String initials, String packName)
    {
        _id = "null";
        fkPackId = packId;
        operation = operationType;
        scanDate = date;
        scannerInitials = initials;
        scannedPackName = packName;
    }

    public PackRecreateScanRecord(String id, String packId, String operationType, String date, String initials, String packName)
    {
        _id = id;
        fkPackId = packId;
        operation = operationType;
        scanDate = date;
        scannerInitials = initials;
        scannedPackName = packName;
    }

    public String access_Id(String id)
    {
        if(id != null)
            _id = id;
        return _id;
    }

    public String accessFkPackId(String packId)
    {
        if(packId != null)
            fkPackId = packId;
        return fkPackId;
    }

    public String accessOperation(String operationType)
    {
        if(operationType != null)
            operation = operationType;
        return operation;
    }

    public String accessScanDate(String date)
    {
        if(date != null)
            scanDate = date;
        return scanDate;
    }

    public String accessScannerInitials(String initials)
    {
        if(initials != null)
            scannerInitials = initials;
        return scannerInitials;
    }

    public String accessScannedPackName(String packName)
    {
        if(packName != null)
            scannedPackName = packName;
        return scannedPackName;
    }

    @Override
    public DataRecord newCopy()
    {
        return new PackRecreateScanRecord(_id, fkPackId, operation, scanDate, scannerInitials, scannedPackName);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case PackRecreateScanContract._ID:
                access_Id(data);
                break;
            case PackRecreateScanContract.COLUMN_NAME_FKPACKID:
                accessFkPackId(data);
                break;
            case PackRecreateScanContract.COLUMN_NAME_OPERATION:
                accessOperation(data);
                break;
            case PackRecreateScanContract.COLUMN_NAME_SCANDATE:
                accessScanDate(data);
                break;
            case PackRecreateScanContract.COLUMN_NAME_SCANNERINITIALS:
                accessScannerInitials(data);
                break;
            case PackRecreateScanContract.COLUMN_NAME_SCANNEDPACKNAME:
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
            case PackRecreateScanContract._ID:
                value = access_Id(null);
                break;
            case PackRecreateScanContract.COLUMN_NAME_FKPACKID:
                value = accessFkPackId(null);
                break;
            case PackRecreateScanContract.COLUMN_NAME_OPERATION:
                value = accessOperation(null);
                break;
            case PackRecreateScanContract.COLUMN_NAME_SCANDATE:
                value = accessScanDate(null);
                break;
            case PackRecreateScanContract.COLUMN_NAME_SCANNERINITIALS:
                value = accessScannerInitials(null);
                break;
            case PackRecreateScanContract.COLUMN_NAME_SCANNEDPACKNAME:
                value = accessScannedPackName(null);
                break;
        }
        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(PackRecreateScanContract.PACKRECREATESCAN_COLUMNS);

        if(dbCursor.moveToFirst())
        {
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)))
                    success = true;
                setValueByKey(dbCursor.getColumnName(count), dbCursor.getString(count));
            }
        }

        return success;
    }

    @Override
    public void clearRecord()
    {
        access_Id("");
        accessFkPackId("");
        accessOperation("");
        accessScanDate("");
        accessScannerInitials("");
        accessScannedPackName("");
    }

    public void clearForNextScan()
    {
        access_Id("");
        accessFkPackId("");
        accessOperation("");
        accessScanDate("");
        accessScannedPackName("");
    }
}
