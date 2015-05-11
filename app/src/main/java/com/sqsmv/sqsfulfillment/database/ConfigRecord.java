package com.sqsmv.sqsfulfillment.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigRecord implements DataRecord
{
    private String pkConfigId;
    private String fkPackId;
    private String configName;
    private String fkSubPackIds;
    private String isValid;
    private String sha;

    private ArrayList<String> subPackIdList;

    public ConfigRecord()
    {
        pkConfigId = "";
        fkPackId = "";
        configName = "";
        fkSubPackIds = "";
        isValid = "";
        sha = "";
        subPackIdList = new ArrayList<>();
    }

    public ConfigRecord(String configId, String packId, String name, String subPackIds, String isValidConfig, String shaVal)
    {
        pkConfigId = configId;
        fkPackId = packId;
        configName = name;
        fkSubPackIds = subPackIds;
        isValid = isValidConfig;
        sha = shaVal;
        subPackIdList = new ArrayList<>();
        splitConfigPackIds();
    }

    public String accessPkConfigId(String configId)
    {
        if(configId != null)
            pkConfigId = configId;
        return pkConfigId;
    }

    public String accessFkPackId(String packId)
    {
        if(packId != null)
            fkPackId = packId;
        return fkPackId;
    }

    public String accessConfigName(String name)
    {
        if(name != null)
            configName = name;
        return configName;
    }

    public String accessFkSubPackIds(String subPackIds)
    {
        if(subPackIds != null)
        {
            fkSubPackIds = subPackIds;
            splitConfigPackIds();
        }
        return fkSubPackIds;
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
        return new ConfigRecord(pkConfigId, fkPackId, configName, fkSubPackIds, isValid, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case ConfigContract.COLUMN_NAME_PKCONFIGID:
                accessPkConfigId(data);
                break;
            case ConfigContract.COLUMN_NAME_FKPACKID:
                accessFkPackId(data);
                break;
            case ConfigContract.COLUMN_NAME_CONFIGNAME:
                accessConfigName(data);
                break;
            case ConfigContract.COLUMN_NAME_FKSUBPACKIDS:
                accessFkSubPackIds(data);
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
            case ConfigContract.COLUMN_NAME_FKPACKID:
                value = accessFkPackId(null);
                break;
            case ConfigContract.COLUMN_NAME_CONFIGNAME:
                value = accessConfigName(null);
                break;
            case ConfigContract.COLUMN_NAME_FKSUBPACKIDS:
                value = accessFkSubPackIds(null);
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
        accessFkPackId("");
        accessConfigName("");
        accessFkSubPackIds("");
        accessIsValid("");
        accessSha("");
        subPackIdList.clear();
    }

    public void splitConfigPackIds()
    {
        subPackIdList.clear();
        subPackIdList.addAll(Arrays.asList(accessFkSubPackIds(null).split(",")));
        Collections.sort(subPackIdList);
    }

    public ArrayList<String> getConfigPackIdList()
    {
        return new ArrayList<>(subPackIdList);
    }

    public int getNumConfigIds()
    {
        return subPackIdList.size();
    }

    public boolean hasConfigPackId(String configPackId)
    {
        return subPackIdList.contains(configPackId);
    }

    public boolean matchesConfigPackIds(ArrayList<String> packIdList)
    {
        boolean matches = true;
        int count = 0;
        if(getNumConfigIds() != packIdList.size())
            matches = false;
        while(matches && count < getNumConfigIds())
        {
            if(!subPackIdList.get(count).equals(packIdList.get(count)))
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
