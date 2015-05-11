package com.sqsmv.sqsfulfillment.database;

import android.content.Context;

public class PackDataAccess extends XMLDataAccess
{
    public PackDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        PackContract packContract = new PackContract();
        contract = packContract;
        xmlContract = packContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new PackRecord();
    }
}
