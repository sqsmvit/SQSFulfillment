package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigRecord implements DataRecord
{
    private String pkConfigId;
    private String fkDoublePackId;
    private String configNum;
    private String fkConfigPackIds;
    private String isValid;
    private String sha;

    private ArrayList<String> configPackIdList;

    public ConfigRecord()
    {
        pkConfigId = "";
        fkDoublePackId = "";
        configNum = "";
        fkConfigPackIds = "";
        isValid = "";
        sha = "";
        configPackIdList = new ArrayList<>();
    }

    public ConfigRecord(String configId, String doublePackId, String configNumber, String configPackIds, String isValidConfig, String shaVal)
    {
        pkConfigId = configId;
        fkDoublePackId = doublePackId;
        configNum = configNumber;
        fkConfigPackIds = configPackIds;
        isValid = isValidConfig;
        sha = shaVal;
        configPackIdList = new ArrayList<>();
        splitConfigPackIds();
    }

    public String accessPkConfigId(String configId)
    {
        if(configId != null)
            pkConfigId = configId;
        return pkConfigId;
    }

    public String accessFkDoublePackId(String doublePackId)
    {
        if(doublePackId != null)
            fkDoublePackId = doublePackId;
        return fkDoublePackId;
    }

    public String accessConfigNum(String configNumber)
    {
        if(configNumber != null)
            configNum = configNumber;
        return configNum;
    }

    public String accessFkConfigPackIds(String configPackIds)
    {
        if(configPackIds != null)
        {
            fkConfigPackIds = configPackIds;
            splitConfigPackIds();
        }
        return fkConfigPackIds;
    }

    public String accessIsValid(String isValidConfig)
    {
        if(isValidConfig != null)
            isValid = isValidConfig;
        return isValid;
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
        return new ConfigRecord(pkConfigId, fkDoublePackId, configNum, fkConfigPackIds, isValid, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case ConfigContract.COLUMN_NAME_PKCONFIGID:
                accessPkConfigId(data);
                break;
            case ConfigContract.COLUMN_NAME_FKDOUBLEPACKID:
                accessFkDoublePackId(data);
                break;
            case ConfigContract.COLUMN_NAME_CONFIGNUM:
                accessConfigNum(data);
                break;
            case ConfigContract.COLUMN_NAME_FKCONFIGPACKIDS:
                accessFkConfigPackIds(data);
                break;
            case ConfigContract.COLUMN_NAME_ISVALID:
                accessIsValid(data);
                break;
            case ConfigContract.COLUMN_NAME_SHA:
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
            case ConfigContract.COLUMN_NAME_PKCONFIGID:
                value = accessPkConfigId(null);
                break;
            case ConfigContract.COLUMN_NAME_FKDOUBLEPACKID:
                value = accessFkDoublePackId(null);
                break;
            case ConfigContract.COLUMN_NAME_CONFIGNUM:
                value = accessConfigNum(null);
                break;
            case ConfigContract.COLUMN_NAME_FKCONFIGPACKIDS:
                value = accessFkConfigPackIds(null);
                break;
            case ConfigContract.COLUMN_NAME_ISVALID:
                value = accessIsValid(null);
                break;
            case ConfigContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(ConfigContract.CONFIG_COLUMNS);

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
        accessPkConfigId("");
        accessFkDoublePackId("");
        accessConfigNum("");
        accessFkConfigPackIds("");
        accessIsValid("");
        accessSha("");
        configPackIdList.clear();
    }

    public void splitConfigPackIds()
    {
        configPackIdList.clear();
        configPackIdList.addAll(Arrays.asList(accessFkConfigPackIds(null).split(",")));
        Collections.sort(configPackIdList);
    }

    public ArrayList<String> getConfigPackIdList()
    {
        return new ArrayList(configPackIdList);
    }

    public int getNumConfigIds()
    {
        return configPackIdList.size();
    }

    public boolean hasConfigPackId(String configPackId)
    {
        return configPackIdList.contains(configPackId);
    }

    public boolean matchesConfigPackIds(ArrayList<String> packIdList)
    {
        boolean matches = true;
        int count = 0;
        if(configPackIdList.size() != packIdList.size())
            matches = false;
        while(matches && count < configPackIdList.size())
        {
            if(!configPackIdList.get(count).equals(packIdList.get(count)))
                matches = false;
            count++;
        }
        return matches;
    }

    public static ArrayList<ConfigRecord> buildConfigListWithCursor(Cursor dbCursor)
    {
        ArrayList<ConfigRecord> configRecords = new ArrayList<>();
        List<String> columnList = Arrays.asList(ConfigContract.CONFIG_COLUMNS);

        while(dbCursor.moveToNext())
        {
            boolean success = false;
            ConfigRecord newConfigRecord = new ConfigRecord();
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)) && !dbCursor.getColumnName(count).equals(XMLFileContract.COLUMN_NAME_SHA))
                    success = true;
                newConfigRecord.setValueByKey(dbCursor.getColumnName(count), dbCursor.getString(count));
            }
            if(success)
                configRecords.add(newConfigRecord);
        }
        dbCursor.close();
        return configRecords;
    }
}
