package com.sqsmv.sqsfulfillment.database.unimplemented;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

public class PackRecreateRulesDataAccess extends XMLDataAccess
{
    public PackRecreateRulesDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        PackRecreateRulesContract packRecreateRulesContract = new PackRecreateRulesContract();
        contract = packRecreateRulesContract;
        xmlContract = packRecreateRulesContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new PackRecreateRulesRecord();
    }
}
