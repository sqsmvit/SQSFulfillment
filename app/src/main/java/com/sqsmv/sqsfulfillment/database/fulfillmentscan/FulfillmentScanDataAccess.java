package com.sqsmv.sqsfulfillment.database.fulfillmentscan;

import android.content.Context;
import android.database.Cursor;

import com.sqsmv.sqsfulfillment.database.DataAccess;
import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.QueryBuilder;

public class FulfillmentScanDataAccess extends DataAccess
{
    public FulfillmentScanDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        contract = new FulfillmentScanContract();
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new FulfillmentScanRecord();
    }

    public int getTotalRows()
    {
        String selectQuery = QueryBuilder.buildSelectQuery(contract.getTableName(), new String[]{contract.getPrimaryKeyName()}, new String[]{});
        Cursor selectResult = db.rawQuery(selectQuery, null);

        int total = selectResult.getCount();

        selectResult.close();
        return total;
    }
}
