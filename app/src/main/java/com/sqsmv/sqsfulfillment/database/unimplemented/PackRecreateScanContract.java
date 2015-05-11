package com.sqsmv.sqsfulfillment.database.unimplemented;

import android.provider.BaseColumns;

import com.sqsmv.sqsfulfillment.database.DBContract;

public class PackRecreateScanContract implements DBContract, BaseColumns
{
    private static final String TABLE_NAME = "PackRecreateScans";
    protected static final String COLUMN_NAME_FKPACKID = "fkinvoiceid";
    protected static final String COLUMN_NAME_OPERATION = "operation";
    protected static final String COLUMN_NAME_SCANDATE = "scandate";
    protected static final String COLUMN_NAME_SCANNERINITIALS = "scannerinitials";
    protected static final String COLUMN_NAME_SCANNEDPACKNAME = "scannedpackname";

    //Matches order of SQLite Table
    protected static final String[] PACKRECREATESCAN_COLUMNS =
            {
                    _ID,
                    COLUMN_NAME_FKPACKID,
                    COLUMN_NAME_OPERATION,
                    COLUMN_NAME_SCANDATE,
                    COLUMN_NAME_SCANNERINITIALS,
                    COLUMN_NAME_SCANNEDPACKNAME
            };

    protected static final String CREATE_TABLE_PACKRECREATESCAN = "CREATE TABLE " +
            TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_FKPACKID + " TEXT, " +
            COLUMN_NAME_OPERATION + " TEXT, " +
            COLUMN_NAME_SCANDATE + " TEXT, " +
            COLUMN_NAME_SCANNERINITIALS + " TEXT, " +
            COLUMN_NAME_SCANNEDPACKNAME + " TEXT);";

    protected static final String DROP_TABLE_PACKRECREATESCAN = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_PACKRECREATESCAN;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_PACKRECREATESCAN;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return _ID;
    }

    @Override
    public String[] getColumnNames()
    {
        return PACKRECREATESCAN_COLUMNS;
    }
}
