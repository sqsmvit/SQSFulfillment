package com.sqsmv.sqsfulfillment.database.invoice;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLDataAccess;

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
