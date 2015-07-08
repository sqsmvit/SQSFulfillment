package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

public class ShipToRecord implements DataRecord
{
    private String pkShipToId;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipState;
    private String shipZip;
    private String isActive;
    private String sha;

    public ShipToRecord()
    {
        pkShipToId = "";
        shipName = "";
        shipAddress = "";
        shipCity = "";
        shipState = "";
        shipZip = "";
        isActive = "";
        sha = "";
    }

    public ShipToRecord(String shipToId, String name, String address, String city, String state, String zip, String isActiveCustomer, String shaVal)
    {
        pkShipToId = shipToId;
        shipName = name;
        shipAddress = address;
        shipCity = city;
        shipState = state;
        shipZip = zip;
        isActive = isActiveCustomer;
        sha = shaVal;
    }

    public String accessPkShipToId(String shipToId)
    {
        if(shipToId != null)
        {
            pkShipToId = shipToId;
        }
        return pkShipToId;
    }

    public String accessShipName(String name)
    {
        if(name != null)
        {
            shipName = name;
        }
        return shipName;
    }

    public String accessShipAddress(String address)
    {
        if(address != null)
        {
            shipAddress = address;
        }
        return shipAddress;
    }

    public String accessShipCity(String city)
    {
        if(city != null)
        {
            shipCity = city;
        }
        return shipCity;
    }

    public String accessShipState(String state)
    {
        if(state != null)
        {
            shipState = state;
        }
        return shipState;
    }

    public String accessShipZip(String zip)
    {
        if(zip != null)
        {
            shipZip = zip;
        }
        return shipZip;
    }

    public String accessIsActive(String isActiveCustomer)
    {
        if(isActiveCustomer != null)
        {
            isActive = isActiveCustomer;
        }
        return isActive;
    }

    public String accessSha(String shaVal)
    {
        if(shaVal != null)
        {
            sha = shaVal;
        }
        return sha;
    }

    @Override
    public DataRecord newCopy()
    {
        return new ShipToRecord(pkShipToId, shipName, shipAddress, shipCity, shipState, shipZip, isActive, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case ShipToContract.COLUMN_NAME_PKSHIPTOID:
                accessPkShipToId(data);
                break;
            case ShipToContract.COLUMN_NAME_SHIPNAME:
                accessShipName(data);
                break;
            case ShipToContract.COLUMN_NAME_SHIPADDRESS:
                accessShipAddress(data);
                break;
            case ShipToContract.COLUMN_NAME_SHIPCITY:
                accessShipCity(data);
                break;
            case ShipToContract.COLUMN_NAME_SHIPSTATE:
                accessShipState(data);
                break;
            case ShipToContract.COLUMN_NAME_SHIPZIP:
                accessShipZip(data);
                break;
            case ShipToContract.COLUMN_NAME_ISACTIVE:
                accessIsActive(data);
                break;
            case ShipToContract.COLUMN_NAME_SHA:
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
            case ShipToContract.COLUMN_NAME_PKSHIPTOID:
                value = accessPkShipToId(null);
                break;
            case ShipToContract.COLUMN_NAME_SHIPNAME:
                value = accessShipName(null);
                break;
            case ShipToContract.COLUMN_NAME_SHIPADDRESS:
                value = accessShipAddress(null);
                break;
            case ShipToContract.COLUMN_NAME_SHIPCITY:
                value = accessShipCity(null);
                break;
            case ShipToContract.COLUMN_NAME_SHIPSTATE:
                value = accessShipState(null);
                break;
            case ShipToContract.COLUMN_NAME_SHIPZIP:
                value = accessShipZip(null);
                break;
            case ShipToContract.COLUMN_NAME_ISACTIVE:
                value = accessIsActive(null);
                break;
            case ShipToContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(ShipToContract.SHIPTO_COLUMNS);

        if(dbCursor.moveToFirst())
        {
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)) && !dbCursor.getColumnName(count).equals(XMLFileContract.COLUMN_NAME_SHA))
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
        accessPkShipToId("");
        accessShipName("");
        accessShipAddress("");
        accessShipCity("");
        accessShipState("");
        accessShipZip("");
        accessIsActive("");
        accessSha("");
    }

    public String formatAddress()
    {
        String shippingAddress = "";
        if(accessPkShipToId(null).isEmpty())
        {
            shippingAddress = accessShipName(null);
        }
        else
        {
            shippingAddress = accessShipName(null) + "\n" +
                    accessShipAddress(null) + "\n" +
                    accessShipCity(null) + ", " + accessShipState(null) + " " + accessShipZip(null);
        }

        return shippingAddress;
    }
}
