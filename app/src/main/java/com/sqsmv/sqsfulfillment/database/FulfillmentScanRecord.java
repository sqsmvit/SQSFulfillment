package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

public class FulfillmentScanRecord implements DataRecord
{
    private String _id;
    private String fkInvoiceId;
    private String scanDate;
    private String scannerName;
    private String fkConfigId;
    private String fkORPackId;
    private String scannedPackName;

    public FulfillmentScanRecord()
    {
        _id = "";
        fkInvoiceId = "";
        scanDate = "";
        scannerName = "";
        fkConfigId = "";
        fkORPackId = "";
        scannedPackName = "";
    }

    public FulfillmentScanRecord(String invoiceId, String date, String name, String configId, String orPackId, String packName)
    {
        _id = "null";
        fkInvoiceId = invoiceId;
        scanDate = date;
        scannerName = name;
        fkConfigId = configId;
        fkORPackId = orPackId;
        scannedPackName = packName;
    }

    public FulfillmentScanRecord(String recId, String invoiceId, String date, String name, String configId, String orPackId, String packName)
    {
        _id = recId;
        fkInvoiceId = invoiceId;
        scanDate = date;
        scannerName = name;
        fkConfigId = configId;
        fkORPackId = orPackId;
        scannedPackName = packName;
    }

    public String access_Id(String recId)
    {
        if(recId != null)
            _id = recId;
        return _id;
    }

    public String accessFkInvoiceId(String invoiceId)
    {
        if(invoiceId != null)
            fkInvoiceId = invoiceId;
        return fkInvoiceId;
    }

    public String accessScanDate(String date)
    {
        if(date != null)
            scanDate = date;
        return scanDate;
    }

    public String accessScannerName(String name)
    {
        if(name != null)
            scannerName = name;
        return scannerName;
    }

    public String accessFkConfigId(String configId)
    {
        if(configId != null)
            fkConfigId = configId;
        return fkConfigId;
    }

    public String accessFkORPackId(String orPackId)
    {
        if(orPackId != null)
            fkORPackId = orPackId;
        return fkORPackId;
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
        return new FulfillmentScanRecord(_id, fkInvoiceId, scanDate, scannerName, fkConfigId, fkORPackId, scannedPackName);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case FulfillmentScanContract._ID:
                access_Id(data);
                break;
            case FulfillmentScanContract.COLUMN_NAME_FKINVOICEID:
                accessFkInvoiceId(data);
                break;
            case FulfillmentScanContract.COLUMN_NAME_SCANDATE:
                accessScanDate(data);
                break;
            case FulfillmentScanContract.COLUMN_NAME_SCANNERNAME:
                accessScannerName(data);
                break;
            case FulfillmentScanContract.COLUMN_NAME_FKCONFIGID:
                accessFkConfigId(data);
                break;
            case FulfillmentScanContract.COLUMN_NAME_FKORPACKID:
                accessFkORPackId(data);
                break;
            case FulfillmentScanContract.COLUMN_NAME_SCANNEDPACKNAME:
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
            case FulfillmentScanContract._ID:
                value = access_Id(null);
                break;
            case FulfillmentScanContract.COLUMN_NAME_FKINVOICEID:
                value = accessFkInvoiceId(null);
                break;
            case FulfillmentScanContract.COLUMN_NAME_SCANDATE:
                value = accessScanDate(null);
                break;
            case FulfillmentScanContract.COLUMN_NAME_SCANNERNAME:
                value = accessScannerName(null);
                break;
            case FulfillmentScanContract.COLUMN_NAME_FKCONFIGID:
                value = accessFkConfigId(null);
                break;
            case FulfillmentScanContract.COLUMN_NAME_FKORPACKID:
                value = accessFkORPackId(null);
                break;
            case FulfillmentScanContract.COLUMN_NAME_SCANNEDPACKNAME:
                value = accessScannedPackName(null);
                break;
        }
        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(FulfillmentScanContract.FULFILLMENTSCAN_COLUMNS);

        if(dbCursor.moveToFirst())
        {
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)))
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
        access_Id("");
        accessFkInvoiceId("");
        accessScanDate("");
        accessScannerName("");
        accessFkConfigId("");
        accessFkORPackId("");
        accessScannedPackName("");
    }

    public void clearForNextScan()
    {
        access_Id("");
        accessFkInvoiceId("");
        accessScanDate("");
        accessFkConfigId("");
        accessFkORPackId("");
        accessScannedPackName("");
    }
}
