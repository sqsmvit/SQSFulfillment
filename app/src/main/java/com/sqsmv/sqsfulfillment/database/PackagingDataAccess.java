package com.sqsmv.sqsfulfillment.database;

import android.content.Context;

public class PackagingDataAccess extends XMLDataAccess
{
    public PackagingDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        PackagingContract packagingContract = new PackagingContract();
        contract = packagingContract;
        xmlContract = packagingContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new PackagingRecord();
    }
}
