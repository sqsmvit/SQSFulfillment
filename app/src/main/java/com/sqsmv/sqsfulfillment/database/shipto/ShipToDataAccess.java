package com.sqsmv.sqsfulfillment.database.shipto;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

public class ShipToDataAccess extends XMLDataAccess
{
    public ShipToDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        ShipToContract shipToContract = new ShipToContract();
        contract = shipToContract;
        xmlContract = shipToContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new ShipToRecord();
    }
}
