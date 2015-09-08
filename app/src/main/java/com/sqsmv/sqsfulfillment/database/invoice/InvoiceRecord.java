package com.sqsmv.sqsfulfillment.database.invoice;

import android.database.Cursor;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.XMLFileContract;

import java.util.Arrays;
import java.util.List;

public class InvoiceRecord implements DataRecord
{
    private String pkInvoiceId;
    private String fkPackId;
    private String fkShipToId;
    private String fkLensIds;
    private String fkShipRequestId;
    private String fkBatchId;
    private String batchName;
    private String docDate;
    private String boxNum;
    private String fkConfigId;
    private String sha;

    public InvoiceRecord()
    {
        pkInvoiceId = "";
        fkPackId = "";
        fkShipToId = "";
        fkLensIds = "";
        fkShipRequestId = "";
        fkBatchId = "";
        batchName = "";
        docDate = "";
        boxNum = "";
        fkConfigId = "";
        sha = "";
    }

    public InvoiceRecord(String invoiceId, String packId, String shipToId, String lensIds, String shipRequestId, String batchId, String bName, String date,
                         String bxNum, String configId, String shaVal)
    {
        pkInvoiceId = invoiceId;
        fkPackId = packId;
        fkShipToId = shipToId;
        fkLensIds = lensIds;
        fkShipRequestId = shipRequestId;
        fkBatchId = batchId;
        batchName = bName;
        docDate = date;
        boxNum = bxNum;
        fkConfigId = configId;
        sha = shaVal;
    }

    public String accessPkInvoiceId(String invoiceId)
    {
        if(invoiceId != null)
        {
            pkInvoiceId = invoiceId;
        }
        return pkInvoiceId;
    }

    public String accessFkPackId(String packId)
    {
        if(packId != null)
        {
            fkPackId = packId;
        }
        return fkPackId;
    }

    public String accessFkShipToId(String shipToId)
    {
        if(shipToId != null)
        {
            fkShipToId = shipToId;
        }
        return fkShipToId;
    }

    public String accessFkLensIds(String lensIds)
    {
        if(lensIds != null)
        {
            fkLensIds = lensIds;
        }
        return fkLensIds;
    }

    public String accessFkShipRequestId(String shipRequestId)
    {
        if(shipRequestId != null)
        {
            fkShipRequestId = shipRequestId;
        }
        return fkShipRequestId;
    }

    public String accessFkBatchId(String batchId)
    {
        if(batchId != null)
        {
            fkBatchId = batchId;
        }
        return fkBatchId;
    }

    public String accessBatchName(String bName)
    {
        if(bName != null)
        {
            batchName = bName;
        }
        return batchName;
    }

    public String accessDocDate(String date)
    {
        if(date != null)
        {
            docDate = date;
        }
        return docDate;
    }

    public String accessBoxNum(String bxNum)
    {
        if(bxNum != null)
        {
            boxNum = bxNum;
        }
        return boxNum;
    }

    public String accessFkConfigId(String configId)
    {
        if(configId != null)
        {
            fkConfigId = configId;
        }
        return fkConfigId;
    }

    public String accessSha(String shaVal)
    {
        if(shaVal != null)
        {
            sha = shaVal;
        }
        return sha;
    }

    @Override
    public DataRecord newCopy()
    {
        return new InvoiceRecord(pkInvoiceId, fkPackId, fkShipToId, fkLensIds, fkShipRequestId, fkBatchId, batchName, docDate, boxNum, fkConfigId, sha);
    }

    @Override
    public void setValueByKey(String key, String data)
    {
        switch(key)
        {
            case InvoiceContract.COLUMN_NAME_PKINVOICEID:
                accessPkInvoiceId(data);
                break;
            case InvoiceContract.COLUMN_NAME_FKPACKID:
                accessFkPackId(data);
                break;
            case InvoiceContract.COLUMN_NAME_FKSHIPTOID:
                accessFkShipToId(data);
                break;
            case InvoiceContract.COLUMN_NAME_FKLENSIDS:
                accessFkLensIds(data);
                break;
            case InvoiceContract.COLUMN_NAME_FKSHIPREQUESTID:
                accessFkShipRequestId(data);
                break;
            case InvoiceContract.COLUMN_NAME_FKBATCHID:
                accessFkBatchId(data);
                break;
            case InvoiceContract.COLUMN_NAME_BATCHNAME:
                accessBatchName(data);
                break;
            case InvoiceContract.COLUMN_NAME_DOCDATE:
                accessDocDate(data);
                break;
            case InvoiceContract.COLUMN_NAME_BOXNUM:
                accessBoxNum(data);
                break;
            case InvoiceContract.COLUMN_NAME_FKCONFIGID:
                accessFkConfigId(data);
                break;
            case InvoiceContract.COLUMN_NAME_SHA:
                accessSha(data);
                break;
        }
    }

    @Override
    public String getValueByKey(String key)
    {
        String value = "";

        switch(key)
        {
            case InvoiceContract.COLUMN_NAME_PKINVOICEID:
                value = accessPkInvoiceId(null);
                break;
            case InvoiceContract.COLUMN_NAME_FKPACKID:
                value = accessFkPackId(null);
                break;
            case InvoiceContract.COLUMN_NAME_FKSHIPTOID:
                value = accessFkShipToId(null);
                break;
            case InvoiceContract.COLUMN_NAME_FKLENSIDS:
                value = accessFkLensIds(null);
                break;
            case InvoiceContract.COLUMN_NAME_FKSHIPREQUESTID:
                value = accessFkShipRequestId(null);
                break;
            case InvoiceContract.COLUMN_NAME_FKBATCHID:
                value = accessFkBatchId(null);
                break;
            case InvoiceContract.COLUMN_NAME_BATCHNAME:
                value = accessBatchName(null);
                break;
            case InvoiceContract.COLUMN_NAME_DOCDATE:
                value = accessDocDate(null);
                break;
            case InvoiceContract.COLUMN_NAME_BOXNUM:
                value = accessBoxNum(null);
                break;
            case InvoiceContract.COLUMN_NAME_FKCONFIGID:
                value = accessFkConfigId(null);
                break;
            case InvoiceContract.COLUMN_NAME_SHA:
                value = accessSha(null);
                break;
        }

        return value;
    }

    @Override
    public boolean buildWithCursor(Cursor dbCursor)
    {
        boolean success = false;
        List<String> columnList = Arrays.asList(InvoiceContract.INVOICE_COLUMNS);

        if(dbCursor.moveToFirst())
        {
            for(int count = 0; count < dbCursor.getColumnCount(); count++)
            {
                if(columnList.contains(dbCursor.getColumnName(count)) && !dbCursor.getColumnName(count).equals(XMLFileContract.COLUMN_NAME_SHA))
                {
                    success = true;
                }
                setValueByKey(dbCursor.getColumnName(count), dbCursor.getString(count));
            }
        }
        dbCursor.close();
        return success;
    }

    @Override
    public void clearRecord()
    {
        accessPkInvoiceId("");
        accessFkPackId("");
        accessFkShipToId("");
        accessFkLensIds("");
        accessFkShipRequestId("");
        accessFkBatchId("");
        accessBatchName("");
        accessDocDate("");
        accessBoxNum("");
        accessFkConfigId("");
        accessSha("");
    }

    public String formatDisplay()
    {
        String invoiceOutput = "";
        if(!accessPkInvoiceId(null).isEmpty())
        {
            invoiceOutput = "Invoice#: " + accessPkInvoiceId(null) +
                    "\nDocDate: " + accessDocDate(null) +
                    "\nBox: " + boxNum;
        }
        return invoiceOutput;
    }
}
