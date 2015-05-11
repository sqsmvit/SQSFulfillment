package com.sqsmv.sqsfulfillment.database;

import android.content.Context;

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
