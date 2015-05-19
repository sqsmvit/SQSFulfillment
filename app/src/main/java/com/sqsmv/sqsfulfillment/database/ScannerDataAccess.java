package com.sqsmv.sqsfulfillment.database;

import android.content.Context;

public class ScannerDataAccess extends XMLDataAccess
{
    public ScannerDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        ScannerContract scannerContract = new ScannerContract();
        contract = scannerContract;
        xmlContract = scannerContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new ScannerRecord();
    }
}
