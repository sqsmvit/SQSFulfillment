package com.sqsmv.sqsfulfillment.database.lens;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

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
