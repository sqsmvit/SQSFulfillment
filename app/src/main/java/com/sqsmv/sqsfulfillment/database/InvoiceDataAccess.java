package com.sqsmv.sqsfulfillment.database;

import android.content.Context;

public class InvoiceDataAccess extends XMLDataAccess
{
    public InvoiceDataAccess(Context activityContext)
    {
        super(activityContext);
    }

    @Override
    public void initContract()
    {
        InvoiceContract invoiceContract = new InvoiceContract();
        contract = invoiceContract;
        xmlContract = invoiceContract;
    }

    @Override
    public DataRecord getDataRecordInstance()
    {
        return new InvoiceRecord();
    }
}
