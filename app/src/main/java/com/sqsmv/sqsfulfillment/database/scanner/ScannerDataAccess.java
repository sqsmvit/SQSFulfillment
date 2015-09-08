package com.sqsmv.sqsfulfillment.database.scanner;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

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
