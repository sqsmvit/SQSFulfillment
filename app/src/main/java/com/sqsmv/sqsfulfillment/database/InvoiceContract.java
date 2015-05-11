package com.sqsmv.sqsfulfillment.database;

public class InvoiceContract implements XMLFileContract
{
	private static final String TABLE_NAME = "Invoice";
    private static final String XML_FILE_NAME = "invoice.xml";
    protected static final String COLUMN_NAME_PKINVOICEID = "pkinvoiceid";
	protected static final String COLUMN_NAME_FKPACKID = "fkpackid";
    protected static final String COLUMN_NAME_FKSHIPTOID = "fkshiptoid";
    protected static final String COLUMN_NAME_FKLENSIDS = "fklensids";
    protected static final String COLUMN_NAME_FKSHIPREQUESTID = "fkshiprequestid";
    protected static final String COLUMN_NAME_FKBATCHID = "fkbatchid";
    protected static final String COLUMN_NAME_BATCHNAME = "batchname";
    protected static final String COLUMN_NAME_DOCDATE = "docdate";
    protected static final String COLUMN_NAME_BOXNUM = "boxnum";
    protected static final String COLUMN_NAME_FKCONFIGID = "fkconfigid";

    //Matches order of SQLite Table and XML
    protected static final String[] INVOICE_COLUMNS =
            {
                    COLUMN_NAME_PKINVOICEID,
                    COLUMN_NAME_FKPACKID,
                    COLUMN_NAME_FKSHIPTOID,
                    COLUMN_NAME_FKLENSIDS,
                    COLUMN_NAME_FKSHIPREQUESTID,
                    COLUMN_NAME_FKBATCHID,
                    COLUMN_NAME_BATCHNAME,
                    COLUMN_NAME_DOCDATE,
                    COLUMN_NAME_BOXNUM,
                    COLUMN_NAME_FKCONFIGID,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_INVOICE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKINVOICEID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_FKPACKID + " TEXT, " +
            COLUMN_NAME_FKSHIPTOID + " TEXT, " +
            COLUMN_NAME_FKLENSIDS + " TEXT, " +
            COLUMN_NAME_FKSHIPREQUESTID + " TEXT, " +
            COLUMN_NAME_FKBATCHID + " TEXT, " +
            COLUMN_NAME_BATCHNAME + " TEXT, " +
            COLUMN_NAME_DOCDATE + " TEXT, " +
            COLUMN_NAME_BOXNUM + " TEXT, " +
            COLUMN_NAME_FKCONFIGID + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_INVOICE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_INVOICE;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_INVOICE;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKINVOICEID;
    }

    @Override
    public String[] getColumnNames()
    {
        return INVOICE_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
