package com.sqsmv.sqsfulfillment.database;

import android.content.Context;
import android.database.Cursor;

public class ConfigDataAccess extends XMLDataAccess
{
    public ConfigDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        ConfigContract configContract = new ConfigContract();
        contract = configContract;
        xmlContract = configContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new ConfigRecord();
    }

    public Cursor selectByDoublePackId(String doublePackId)
    {
        String selectQuery = QueryBuilder.buildSelectQuery(contract.getTableName(), new String[]{"*"}, new String[]{ConfigContract.COLUMN_NAME_FKDOUBLEPACKID});
        Cursor selectResult = db.rawQuery(selectQuery, new String[]{doublePackId});
        return selectResult;
    }
}
