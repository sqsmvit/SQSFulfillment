package com.sqsmv.sqsfulfillment.database.fulfillmentscan;

import android.provider.BaseColumns;

import com.sqsmv.sqsfulfillment.database.DBContract;

public class FulfillmentScanContract implements DBContract, BaseColumns
{
    private static final String TABLE_NAME = "FulfillmentScan";
    public static final String COLUMN_NAME_FKINVOICEID = "fkinvoiceid";
    protected static final String COLUMN_NAME_SCANDATE = "scandate";
    protected static final String COLUMN_NAME_SCANNERNAME = "scannername";
    protected static final String COLUMN_NAME_FKCONFIGID = "fkconfigid";
    protected static final String COLUMN_NAME_FKORPACKID = "fkorpackid";
    public static final String COLUMN_NAME_SCANNEDPACKNAME = "scannedpackname";

    //Matches order of SQLite Table
    protected static final String[] FULFILLMENTSCAN_COLUMNS =
            {
                    _ID,
                    COLUMN_NAME_FKINVOICEID,
                    COLUMN_NAME_SCANDATE,
                    COLUMN_NAME_SCANNERNAME,
                    COLUMN_NAME_FKCONFIGID,
                    COLUMN_NAME_FKORPACKID,
                    COLUMN_NAME_SCANNEDPACKNAME
            };
    protected static final String CREATE_TABLE_FULFILLMENTSCAN = "CREATE TABLE " +
            TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_FKINVOICEID + " TEXT, " +
            COLUMN_NAME_SCANDATE + " TEXT, " +
            COLUMN_NAME_SCANNERNAME + " TEXT, " +
            COLUMN_NAME_FKCONFIGID + " TEXT, " +
            COLUMN_NAME_FKORPACKID + " TEXT, " +
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
