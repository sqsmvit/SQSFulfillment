package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

public class PackRecord implements DataRecord
{
    private String pkPackId;
    private String fkLensId;
    private String fkBoxMasnum;
    private String packName;
    private int prodQuantity;
    private int numPackLines;
    private String isDouble;
    private String isValid;
    private String countOnHand;
    private String pullQueued;
    private String sha;

    public PackRecord()
    {
        pkPackId = "";
        fkLensId = "";
        fkBoxMasnum = "";
        packName = "";
        prodQuantity = 0;
        numPackLines = 0;
        isDouble = "";
        sha = "";
    }

    public PackRecord(String packId, String lensId, String boxMasnum, String name, int totalQuantity, int numLines, String isDoublePack, String isValidPack, String inventoryCount,
                      String pullCount, String shaVal)
    {
        pkPackId = packId;
        fkLensId = lensId;
        fkBoxMasnum = boxMasnum;
        packName = name;
        prodQuantity = totalQuantity;
        numPackLines = numLines;
        isDouble = isDoublePack;
        isValid = isValidPack;
        countOnHand = inventoryCount;
        pullQueued = pullCount;
        sha = shaVal;
    }

    public String accessPkPackId(String packId)
    {
        if(packId != null)
        {
            pkPackId = packId;
        }
        return pkPackId;
    }

    public String accessFkLensId(String lensId)
    {
        if(lensId != null)
        {
            fkLensId = lensId;
        }
        return fkLensId;
    }

    public String accessFkBoxMasnum(String boxMasnum)
    {
        if(boxMasnum != null)
        {
            fkBoxMasnum = boxMasnum;
        }
        return fkBoxMasnum;
    }

    public String accessPackName(String name)
    {
        if(name != null)
        {
            packName = name;
        }
        return packName;
    }

    public int accessProdQuantity(Integer totalQuantity)
    {
        if(totalQuantity != null)
        {
            prodQuantity = totalQuantity.intValue();
        }
        return prodQuantity;
    }

    public int accessNumPackLines(Integer numLines)
    {
        if(numLines != null)
        {
            numPackLines = numLines.intValue();
        }
        return numPackLines;
    }

    public String accessIsDouble(String isDoublePack)
    {
        if(isDoublePack != null)
        {
            isDouble = isDoublePack;
        }
        return isDouble;
    }

    public String accessIsValid(String isValidPack)
    {
        if(isValidPack != null)
        {
            isValid = isValidPack;
        }
        return isValid;
    }

    public String accessCountOnHand(String inventoryCount)
    {
        if(inventoryCount != null)
        {
            countOnHand = inventoryCount;
        }
        return countOnHand;
    }

    public String accessPullQueued(String pullCount)
    {
        if(pullCount != null)
        {
            pullQueued = pullCount;
        }
        return pullQueued;
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
        return new PackRecord(pkPackId, fkLensId, fkBoxMasnum, packName, prodQuantity, numPackLines, isDouble, isValid, countOnHand, pullQueued, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case PackContract.COLUMN_NAME_PKPACKID:
                accessPkPackId(data);
                break;
            case PackContract.COLUMN_NAME_FKLENSID:
                accessFkLensId(data);
                break;
            case PackContract.COLUMN_NAME_FKBOXMASNUM:
                accessFkBoxMasnum(data);
                break;
            case PackContract.COLUMN_NAME_PACKNAME:
                accessPackName(data);
                break;
            case PackContract.COLUMN_NAME_PRODQUANTITY:
                accessProdQuantity(Integer.valueOf(data));
                break;
            case PackContract.COLUMN_NAME_NUMPACKLINES:
                accessNumPackLines(Integer.valueOf(data));
                break;
            case PackContract.COLUMN_NAME_ISDOUBLE:
                accessIsDouble(data);
                break;
            case PackContract.COLUMN_NAME_ISVALID:
                accessIsValid(data);
                break;
            case PackContract.COLUMN_NAME_COUNTONHAND:
                accessCountOnHand(data);
                break;
            case PackContract.COLUMN_NAME_PULLQUEUED:
                accessPullQueued(data);
                break;
            case PackContract.COLUMN_NAME_SHA:
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
            case PackContract.COLUMN_NAME_PKPACKID:
                value = accessPkPackId(null);
                break;
            case PackContract.COLUMN_NAME_FKLENSID:
                value = accessFkLensId(null);
                break;
            case PackContract.COLUMN_NAME_FKBOXMASNUM:
                value = accessFkBoxMasnum(null);
                break;
            case PackContract.COLUMN_NAME_PACKNAME:
                value = accessPackName(null);
                break;
            case PackContract.COLUMN_NAME_PRODQUANTITY:
                value = Integer.toString(accessProdQuantity(null));
                break;
            case PackContract.COLUMN_NAME_NUMPACKLINES:
                value = Integer.toString(accessNumPackLines(null));
                break;
            case PackContract.COLUMN_NAME_ISDOUBLE:
                value = accessIsDouble(null);
                break;
            case PackContract.COLUMN_NAME_ISVALID:
                value = accessIsValid(null);
                break;
            case PackContract.COLUMN_NAME_COUNTONHAND:
                value = accessCountOnHand(null);
                break;
            case PackContract.COLUMN_NAME_PULLQUEUED:
                value = accessPullQueued(null);
                break;
            case PackContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(PackContract.PACK_COLUMNS);

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
        accessPkPackId("");
        accessFkBoxMasnum("");
        accessPackName("");
        accessProdQuantity(0);
        accessNumPackLines(0);
        accessIsDouble("");
        accessIsValid("");
        accessCountOnHand("");
        accessPullQueued("");
        accessSha("");
    }

    public String formatDisplay()
    {
        String packOutput;
        if(accessPkPackId(null).isEmpty())
        {
            packOutput = accessPackName(null);
        }
        else
        {
            packOutput = accessPackName(null) + "\n" +
                    "\tQuantity: " + accessProdQuantity(null) +
                    "\n\tLine Items: " + accessNumPackLines(null) +
                    "\n\tStart of Day CoH: " + accessCountOnHand(null) +
                    "\n\tStart of Day Pull Queue: " + accessPullQueued(null);
            if(accessIsValid(null).isEmpty())
            {
                packOutput = "Needs Validation: " + packOutput;
            }
        }

        return packOutput;
    }
}
