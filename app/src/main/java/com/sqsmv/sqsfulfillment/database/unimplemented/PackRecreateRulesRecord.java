package com.sqsmv.sqsfulfillment.database.unimplemented;

import android.database.Cursor;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLFileContract;

import java.util.Arrays;
import java.util.List;

public class PackRecreateRulesRecord implements DataRecord
{
    private String pkRulesId;
    private String fkLensId;
    private String allowedFkLensId;
    private String sha;

    public PackRecreateRulesRecord()
    {
        pkRulesId = "";
        fkLensId = "";
        allowedFkLensId = "";
        sha = "";
    }

    public PackRecreateRulesRecord(String rulesId, String lensId, String allowedLensId, String shaVal)
    {
        pkRulesId = rulesId;
        fkLensId = lensId;
        allowedFkLensId = allowedLensId;
        sha = shaVal;
    }

    public String accessPkRulesId(String rulesId)
    {
        if(rulesId != null)
            pkRulesId = rulesId;
        return pkRulesId;
    }

    public String accessFkLensId(String lensId)
    {
        if(lensId != null)
            fkLensId = lensId;
        return fkLensId;
    }

    public String accessAllowedFkLensId(String allowedLensId)
    {
        if(allowedLensId != null)
            allowedFkLensId = allowedLensId;
        return allowedFkLensId;
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
        return new PackRecreateRulesRecord(pkRulesId, fkLensId, allowedFkLensId, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case PackRecreateRulesContract.COLUMN_NAME_PKRULESID:
                accessPkRulesId(data);
                break;
            case PackRecreateRulesContract.COLUMN_NAME_FKLENSID:
                accessFkLensId(data);
                break;
            case PackRecreateRulesContract.COLUMN_NAME_ALLOWEDFKLENSID:
                accessAllowedFkLensId(data);
                break;
            case PackRecreateRulesContract.COLUMN_NAME_SHA:
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
            case PackRecreateRulesContract.COLUMN_NAME_PKRULESID:
                value = accessPkRulesId(null);
                break;
            case PackRecreateRulesContract.COLUMN_NAME_FKLENSID:
                value = accessFkLensId(null);
                break;
            case PackRecreateRulesContract.COLUMN_NAME_ALLOWEDFKLENSID:
                value = accessAllowedFkLensId(null);
                break;
            case PackRecreateRulesContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(PackRecreateRulesContract.PACKRECREATERULES_COLUMNS);

        if(dbCursor.moveToFirst())
        {
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)) && !dbCursor.getColumnName(count).equals(XMLFileContract.COLUMN_NAME_SHA))
                    success = true;
                setValueByKey(dbCursor.getColumnName(count), dbCursor.getString(count));
            }
        }

        return success;
    }

    @Override
    public void clearRecord()
    {
        accessPkRulesId("");
        accessFkLensId("");
        accessAllowedFkLensId("");
        accessSha("");
    }
}
