package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackLineRecord implements DataRecord
{
    private String pkPackLineId;
    private String fkPackId;
    private String productName;
    private int quantity;
    private String pricePoint;
    private String sha;

    public PackLineRecord()
    {
        pkPackLineId = "";
        fkPackId = "";
        productName = "";
        quantity = 0;
        pricePoint = "";
        sha = "";
    }

    public PackLineRecord(String packLineId, String packId, String name, int totalQuantity, String packLinePricePoint, String shaVal)
    {
        pkPackLineId = packLineId;
        fkPackId = packId;
        productName = name;
        quantity = totalQuantity;
        pricePoint = packLinePricePoint;
        sha = shaVal;
    }

    public String accessPkPackLineId(String packLineId)
    {
        if(packLineId != null)
            pkPackLineId = packLineId;
        return pkPackLineId;
    }

    public String accessFkPackId(String packId)
    {
        if(packId != null)
            fkPackId = packId;
        return fkPackId;
    }

    public String accessProductName(String name)
    {
        if(name != null)
            productName = name;
        return productName;
    }

    public int accessQuantity(Integer totalQuantity)
    {
        if(totalQuantity != null)
            quantity = totalQuantity.intValue();
        return quantity;
    }

    public String accessPricePoint(String packLinePricePoint)
    {
        if(packLinePricePoint != null)
            pricePoint = packLinePricePoint;
        return pricePoint;
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
        return new PackLineRecord(pkPackLineId, fkPackId, productName, quantity, pricePoint, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case PackLineContract.COLUMN_NAME_PKPACKLINEID:
                accessPkPackLineId(data);
                break;
            case PackLineContract.COLUMN_NAME_FKPACKID:
                accessFkPackId(data);
                break;
            case PackLineContract.COLUMN_NAME_PRODUCTNAME:
                accessProductName(data);
                break;
            case PackLineContract.COLUMN_NAME_QUANTITY:
                accessQuantity(Integer.valueOf(data));
                break;
            case PackLineContract.COLUMN_NAME_PRICEPOINT:
                accessPricePoint(data);
                break;
            case PackLineContract.COLUMN_NAME_SHA:
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
            case PackLineContract.COLUMN_NAME_PKPACKLINEID:
                value = accessPkPackLineId(null);
                break;
            case PackLineContract.COLUMN_NAME_FKPACKID:
                value = accessFkPackId(null);
                break;
            case PackLineContract.COLUMN_NAME_PRODUCTNAME:
                value = accessProductName(null);
                break;
            case PackLineContract.COLUMN_NAME_QUANTITY:
                value = Integer.toString(accessQuantity(null));
                break;
            case PackLineContract.COLUMN_NAME_PRICEPOINT:
                value = accessPricePoint(null);
                break;
            case PackLineContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(PackLineContract.PACKLINE_COLUMNS);

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
        accessPkPackLineId("");
        accessFkPackId("");
        accessProductName("");
        accessQuantity(0);
        accessPricePoint("");
        accessSha("");
    }

    public static ArrayList<PackLineRecord> buildPackLineListWithCursor(Cursor dbCursor)
    {
        ArrayList<PackLineRecord> packLineRecords = new ArrayList<>();
        List<String> columnList = Arrays.asList(PackLineContract.PACKLINE_COLUMNS);

        while(dbCursor.moveToNext())
        {
            boolean success = false;
            PackLineRecord newPackLineRecord = new PackLineRecord();
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)) && !dbCursor.getColumnName(count).equals(XMLFileContract.COLUMN_NAME_SHA))
                    success = true;
                newPackLineRecord.setValueByKey(dbCursor.getColumnName(count), dbCursor.getString(count));
            }
            if(success)
                packLineRecords.add(newPackLineRecord);
        }
        dbCursor.close();
        return packLineRecords;
    }
}
