package com.sqsmv.sqsfulfillment.database;

import android.content.Context;

public class LensDataAccess extends XMLDataAccess
{
    public LensDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        LensContract lensContract = new LensContract();
        contract = lensContract;
        xmlContract = lensContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new LensRecord();
    }
}
