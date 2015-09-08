package com.sqsmv.sqsfulfillment.database.packaging;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

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
