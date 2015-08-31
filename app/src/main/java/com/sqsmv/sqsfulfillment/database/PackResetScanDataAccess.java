package com.sqsmv.sqsfulfillment.database;

import android.content.Context;
import android.database.Cursor;

public class PackResetScanDataAccess extends DataAccess
{
    public PackResetScanDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        contract = new PackResetScanContract();
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new PackResetScanRecord();
    }

    public Cursor selectReverseOrder()
    {
        String[] cols = new String[]{contract.getPrimaryKeyName(), PackResetScanContract.COLUMN_NAME_SCANNEDPACKNAME, PackResetScanContract.COLUMN_NAME_QUANTITY};
        String orderBy = contract.getPrimaryKeyName() + " DESC";
        return db.query(contract.getTableName(), cols, null, null, null, null, orderBy);
    }


    public int getTotalPacks()
    {
        int total = 0;
        String[] cols = new String[]{"SUM(" + PackResetScanContract.COLUMN_NAME_QUANTITY + ") As Total"};
        Cursor selectResult = db.query(contract.getTableName(), cols, null, null, null, null, null);

        if(selectResult.moveToFirst())
        {
            if(selectResult.getString(0) != null && !selectResult.getString(0).isEmpty())
            {
                total = selectResult.getInt(0);
            }
        }

        selectResult.close();
        return total;
    }
}
