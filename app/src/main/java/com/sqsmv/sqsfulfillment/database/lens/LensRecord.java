package com.sqsmv.sqsfulfillment.database.lens;

import android.database.Cursor;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLFileContract;

import java.util.Arrays;
import java.util.List;

public class LensRecord implements DataRecord
{
    private String pkLensId;
    private String lensName;
    private String sha;

    public LensRecord()
    {
        pkLensId = "";
        lensName = "";
        sha = "";
    }

    public LensRecord(String lensId, String name, String shaVal)
    {
        pkLensId = lensId;
        lensName = name;
        sha = shaVal;
    }

    public String accessPkLensId(String lensId)
    {
        if(lensId != null)
        {
            pkLensId = lensId;
        }
        return pkLensId;
    }

    public String accessLensName(String name)
    {
        if(name != null)
        {
            lensName = name;
        }
        return lensName;
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
        return new LensRecord(pkLensId, lensName, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case LensContract.COLUMN_NAME_PKLENSID:
                accessPkLensId(data);
                break;
            case LensContract.COLUMN_NAME_LENSNAME:
                accessLensName(data);
                break;
            case LensContract.COLUMN_NAME_SHA:
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
            case LensContract.COLUMN_NAME_PKLENSID:
                value = accessPkLensId(null);
                break;
            case LensContract.COLUMN_NAME_LENSNAME:
                value = accessLensName(null);
                break;
            case LensContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(LensContract.LENS_COLUMNS);

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
        accessPkLensId("");
        accessLensName("");
        accessSha("");
    }
}
