package com.sqsmv.sqsfulfillment.database.unimplemented;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataAccess;
import com.sqsmv.sqsfulfillment.database.DataRecord;

public class PackRecreateScanDataAccess extends DataAccess
{
    public PackRecreateScanDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        contract = new PackRecreateScanContract();
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new PackRecreateScanRecord();
    }
/*
    public void insertScans(ArrayList<PackRecreateScanRecord> batch)
    {
        for(DataRecord record : batch)
        {
            insert(record);
        }
    }
    */
}
