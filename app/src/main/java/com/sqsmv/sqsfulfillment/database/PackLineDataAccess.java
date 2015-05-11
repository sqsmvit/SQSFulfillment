package com.sqsmv.sqsfulfillment.database;

import android.content.Context;
import android.database.Cursor;

public class PackLineDataAccess extends XMLDataAccess
{
    public PackLineDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        PackLineContract packLineContract = new PackLineContract();
        contract = packLineContract;
        xmlContract = packLineContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new PackLineRecord();
    }

    public Cursor selectByPackId(String packId)
    {
        String selectQuery = QueryBuilder.buildSelectQuery(contract.getTableName(), new String[]{"*"}, new String[]{PackLineContract.COLUMN_NAME_FKPACKID});
        Cursor selectResult = db.rawQuery(selectQuery, new String[]{packId});
        return selectResult;
    }
}
