package com.sqsmv.sqsfulfillment.database.pack;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

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
