package com.sqsmv.sqsfulfillment.database.exporttracking;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataAccess;
import com.sqsmv.sqsfulfillment.database.DataRecord;

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
