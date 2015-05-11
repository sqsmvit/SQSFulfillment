package com.sqsmv.sqsfulfillment.database;

import android.provider.BaseColumns;

public class FulfillmentScanContract implements DBContract, BaseColumns
{
    private static final String TABLE_NAME = "FulfillmentScan";
    public static final String COLUMN_NAME_FKINVOICEID = "fkinvoiceid";
    protected static final String COLUMN_NAME_SCANDATE = "scandate";
    protected static final String COLUMN_NAME_SCANNERINITIALS = "scannerinitials";
    protected static final String COLUMN_NAME_FKCONFIGID = "fkconfigid";
    public static final String COLUMN_NAME_SCANNEDPACKNAME = "scannedpackname";

    //Matches order of SQLite Table
    protected static final String[] FULFILLMENTSCAN_COLUMNS =
            {
                    _ID,
                    COLUMN_NAME_FKINVOICEID,
                    COLUMN_NAME_SCANDATE,
                    COLUMN_NAME_SCANNERINITIALS,
                    COLUMN_NAME_FKCONFIGID,
                    COLUMN_NAME_SCANNEDPACKNAME
            };

    protected static final String CREATE_TABLE_FULFILLMENTSCAN = "CREATE TABLE " +
            TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_FKINVOICEID + " TEXT, " +
            COLUMN_NAME_SCANDATE + " TEXT, " +
            COLUMN_NAME_SCANNERINITIALS + " TEXT, " +
            COLUMN_NAME_FKCONFIGID + " TEXT, " +
            COLUMN_NAME_SCANNEDPACKNAME + " TEXT);";

    protected static final String DROP_TABLE_FULFILLMENTSCAN = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_FULFILLMENTSCAN;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_FULFILLMENTSCAN;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return _ID;
    }

    @Override
    public String[] getColumnNames()
    {
        return FULFILLMENTSCAN_COLUMNS;
    }
}
