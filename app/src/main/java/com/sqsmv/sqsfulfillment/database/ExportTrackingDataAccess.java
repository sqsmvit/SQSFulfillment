package com.sqsmv.sqsfulfillment.database;

import android.content.Context;

public class ExportTrackingDataAccess extends DataAccess
{
    public ExportTrackingDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        contract = new ExportTrackingContract();
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new ExportTrackingRecord();
    }
}
